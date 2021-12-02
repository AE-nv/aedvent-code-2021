(ns aoc-2021.day02
  (:require [util :as util]
            [clojure.string :as str]))

(defn read-course [raw]
  (->> raw
       (map (fn [x] (str/split x #" ")))
       (map (fn [[action steps]]
              [(keyword action) (util/to-int steps)]))))

(def submarine [0 0])
(defn forward [[position depth] steps]
  [(+ position steps) depth])
(defn down [[position depth] steps]
  [position (+ depth steps)])
(defn up [[position depth] steps]
  [position (max 0 (- depth steps))])

(defn test-all-the-way-up []
  (= (up [0 2] 4) [0 0]))

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

(defn test-example []
  (= (->> (follow-course submarine example-course)
          (reduce *))
    150))

(def input (util/file->seq "2021/d02.txt"))

(defn -main
  "Main function"
  []
  (println (->> input
                read-course
                (follow-course submarine)
                (reduce *))))