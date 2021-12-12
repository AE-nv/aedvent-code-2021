(ns aoc-2021.day12plus
  (:require [util :as util]
            [clojure.string :as str]))

(def small-example (list
                     "start-A"
                     "start-b"
                     "A-c"
                     "A-b"
                     "b-d"
                     "A-end"
                     "b-end"))

(def start "start")
(def end "end")

(defn parse-segment [line]
  (str/split line #"-"))

(defn parse-path [lines]
  (let [connections (map parse-segment lines)
        one-way-map (->> connections
                         (group-by first)
                         (map (fn [[key connections]] [key (->> (map second connections) (into #{}))]))
                         (into {}))
        reverse-way (->> connections
                         (group-by second)
                         (map (fn [[key connections]] [key (->> (map first connections) (into #{}))]))
                         (into {}))]
    (merge-with into one-way-map reverse-way)))

(defn already-visited? [path node]
  (boolean (some (fn [x] (= x node)) path)))

(defn small-cave-visited-more-than-once [path-so-far]
  (->> (filter (fn [x] (not (util/upper-case? x))) path-so-far)
       frequencies
       (map second)
       (apply max 0)
       (<= 2)))

(defn generate-paths-from [connection-map path-so-far start]
  (let [last (peek path-so-far)
        options (->> (connection-map last)
                     (filter (fn [x] (and
                                       (not= x start)
                                       (or
                                         (util/upper-case? x)
                                         (not (already-visited? path-so-far x))
                                         (not (small-cave-visited-more-than-once path-so-far)))))))
        spawned-paths (map (fn [x] (conj path-so-far x)) options)]
    spawned-paths))

(defn filter-end-reached [end]
  (fn [paths]
    (filter (fn [path] (= end (peek path))) paths)))
(defn filter-end-not-reached [end]
  (fn [paths]
    (filter (fn [path] (not= end (peek path))) paths)))
(defn split-by-end-reached [end paths]
  ((juxt (filter-end-reached end) (filter-end-not-reached end)) paths))

(defn generate-all-paths
  ([connection-map start end]
   (generate-all-paths connection-map #{} #{[start]} start end))
  ([connection-map done todo start end]
   (let [[finished unfinished]
         (reduce (fn [[finished unfinished] path]
                   (let [[reached not-reached :as spawned-paths] (->> (generate-paths-from connection-map path start)
                                                                      (split-by-end-reached end))]
                     (if (seq spawned-paths)
                       [(into finished reached) (into unfinished not-reached)]
                       [(conj finished path) unfinished])))
           [#{} #{}]
           todo)
         new-done (into done finished)]
     (if (seq unfinished)
       (recur connection-map new-done unfinished start end)
       new-done))))

(comment "repls tests"
  ((parse example-input) [9 9])

  (def small-connection-map (parse-path small-example))

  (small-cave-visited-more-than-once-excluding-start ["start" "A" "b" "A" "c" "A" "end"] "start")

  (count (generate-all-paths small-connection-map start end))
  ,)


(defn -main
  "Main function"
  []
  (println (let [connection-map (parse-path (util/file->seq "2021/d12.txt"))]
             (count (generate-all-paths connection-map start end)))))