(ns aoc-2021.day05plus
  (:require [util :as util]
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

;; direction and then plus or minus 1 per step until end is reached

(defn generate-points [[start-x end-x] [start-y end-y]]
  (->> (for [x (range start-x (inc end-x))
             y (range start-y (inc end-y))]
         [x y])
       (into #{})))

(defn generate-points-new [x1 y1 x2 y2]
  (let [start [x1 y1]
        end [x2 y2]
        step-vector (util/normalize [(- x2 x1) (- y2 y1)])]
    (reduce
      (fn [[set [x y]] [x2 y2]]
        (let [total [(+ x x2) (+ y y2)]
              new-set (conj set total)]
          (if (= total end)
            (reduced new-set)
            [new-set total])))
      [#{start} start]
      (repeat step-vector))))

(defn build-segment [segment-def]
  (let [[x1 y1 x2 y2] (->> (re-matches #"(^\d+),(\d+) -> (\d+),(\d+)$" segment-def)
                           (drop 1)
                           (map util/to-int)
                           (apply vector))]
    (generate-points-new x1 y1 x2 y2)))

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

  (def old (parse example-input))
  (def new (parse example-input))
  (= old (parse example-input))

  (->> example-input
       parse
       find-overlapping
       count)



  ,)


(def input (util/file->seq "2021/d05.txt"))

(defn -main
  "Main function"
  []
  (println (->> (util/file->seq "2021/d05.txt")
                parse
                find-overlapping
                count)))