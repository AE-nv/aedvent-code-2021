(ns aoc-2021.day02plus
  (:require [util :as util]
            [clojure.string :as str]))

(defn read-course [raw]
  (->> raw
       (map (fn [x] (str/split x #" ")))
       (map (fn [[action steps]]
              [(keyword action) (util/to-int steps)]))))

(def submarine [0 0 0])
(defn forward [[position depth aim] steps]
  [(+ position steps)
   (max 0 (+ depth (* steps aim)))
   aim])
(defn down [[position depth aim] steps]
  [position depth (+ aim steps)])
(defn up [[position depth aim] steps]
  [position depth (- aim steps)])
(defn straight-distance-travelled [[position depth _]]
  (* position depth))

(comment "repl tests"
  (= (down submarine 5) [0 0 5])
  (= (up submarine 5) [0 0 -5])
  (= (forward submarine 5) [5 0 0])
  (= (forward [0 0 4] 5) [5 20 4])
  "can't go over surface"
  (= (forward [0 6 -4] 5) [5 0 -4])
,)

(defn follow-course [submarine course]
  (reduce (fn [sub [action steps]]
            (condp = action
              :forward (forward sub steps)
              :down (down sub steps)
              :up (up sub steps)
              sub))
    submarine
    course))

(def example-input (list
                     "forward 5"
                     "down 5"
                     "forward 8"
                     "up 3"
                     "down 8"
                     "forward 2"))
(def example-course (read-course example-input))

(comment "test example"
  (= (->> (follow-course submarine example-course)
          straight-distance-travelled)
    900)
  ,)

(def input (util/file->seq "2021/d02.txt"))

(defn -main
  "Main function"
  []
  (println (->> input
                read-course
                (follow-course submarine)
                straight-distance-travelled)))