(ns aoc-2021.day09
  (:require [util :as util]))

(def example-input (list
                     "2199943210"
                     "3987894921"
                     "9856789892"
                     "8767896789"
                     "9899965678"))
(defn parse [grid-lines]
  (->> (map-indexed
         (fn [row-idx row]
           (map-indexed
             (fn [col-idx x] [[row-idx col-idx] (util/to-int (str x))]) row))
         grid-lines)
       (apply concat)
       (into {})))

(defn surrounding-coordinates [[x y]]
  #{[(inc x) y] [(dec x) y] [x (inc y)] [x (dec y)]})

(defn scan-for-minima [grid]
  (reduce (fn [minima [coord value]]
            (let [surrounding (surrounding-coordinates coord)
                  lowest-surrounding (apply min (filter identity (map grid surrounding)))]
              (if (< value lowest-surrounding)
                (conj minima value)
                minima)))
    '()
    grid))

(defn calculate-risk [height] (inc height))

(comment "repls tests"
  (def ex-grid (parse example-input))
  (->> (scan-for-minima ex-grid)
       (map calculate-risk)
       (reduce +))

  ,)


(defn -main
  "Main function"
  []
  (println (->> (util/file->seq "2021/d09.txt")
                parse
                scan-for-minima
                (map calculate-risk)
                (reduce +))))