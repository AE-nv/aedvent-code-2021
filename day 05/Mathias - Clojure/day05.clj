(ns aoc-2021.day05
  (:require [util :as util]
            [clojure.string :as str]
            [clojure.set :as set]))

(def example-input (list
                     "0,9 -> 5,9"
                     "8,0 -> 0,8"
                     "9,4 -> 3,4"
                     "2,2 -> 2,1"
                     "7,0 -> 7,4"
                     "6,4 -> 2,0"
                     "0,9 -> 2,9"
                     "3,4 -> 1,4"
                     "0,0 -> 8,8"
                     "5,5 -> 8,2"))

(defn generate-points [[start-x end-x] [start-y end-y]]
  (->> (for [x (range start-x (inc end-x))
             y (range start-y (inc end-y))]
         [x y])
       (into #{})))

(defn build-segment [segment-def]
  (let [[x1 y1 x2 y2] (->> (re-matches #"(^\d+),(\d+) -> (\d+),(\d+)$" segment-def)
                           (drop 1)
                           (map util/to-int)
                           (apply vector))
        horizontal-range (if (<= x1 x2) [x1 x2] [x2 x1])
        vertical-range (if (<= y1 y2) [y1 y2] [y2 y1])]
    (if (or (= x1 x2) (= y1 y2))
      (generate-points horizontal-range vertical-range)
      #{})))

(defn parse [input]
  (->> (map build-segment input)
       (filter seq)))

(defn find-overlapping [segments]
  (->> (reduce (fn [[encountered overlapping] seg]
                 (let [new-encountered (into encountered seg)
                       overlapping-with-current (set/intersection encountered seg)
                       new-overlapping (into overlapping overlapping-with-current)]
                   [new-encountered new-overlapping]))
         [#{} #{}]
         segments)
       second))

(comment "repl tests"
  (re-matches #"^\d+,\d+ -> \d+,\d+$" "0,9 -> 5,9")
  (re-matches #"(^\d+),(\d+) -> (\d+),(\d+)$" "0,9 -> 5,9")
  (str/split "0,9 -> 5,9" #" -> ")
  (for [x (range 0 (inc 5))] [x 9])

  (extract-segment "0,9 -> 5,9")

  (generate-points 1 5 1 0)
  (build-segment "0,9 -> 5,9")

  (->> (map build-segment example-input)
       (filter seq))

  (into #{1 2 3} #{2 3 5})

  (def segments (parse example-input))
  (reduce (fn [[encountered overlapping] seg]
            (let [new-encountered (into encountered seg)
                  overlapping-with-current (set/intersection encountered seg)
                  new-overlapping (into overlapping overlapping-with-current)]
              [new-encountered new-overlapping]))
    [#{} #{}]
    segments)
  ,)


(def input (util/file->seq "2021/d05.txt"))

(defn -main
  "Main function"
  []
  (println (->> (util/file->seq "2021/d05.txt")
                parse
                find-overlapping
                count)))