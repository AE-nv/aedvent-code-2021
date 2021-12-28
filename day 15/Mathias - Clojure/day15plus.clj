(ns aoc-2021.day15plus
  (:require [util :as util]
            [clojure.data.priority-map :as pmap]))

(def example (list
               "1163751742"
               "1381373672"
               "2136511328"
               "3694931569"
               "7463417111"
               "1319128137"
               "1359912421"
               "3125421639"
               "1293138521"
               "2311944581"))

(defn map-vals [m f]
  (into {} (for [[k v] m] [k (f v)])))

(defn remove-keys [m pred]
  (select-keys m (filter (complement pred) (keys m))))

(defn dijkstra
  "Computes single-source shortest path distances in a directed graph.
   Given a node n, (f n) should return a map with the successors of n as keys
   and their (non-negative) distance from n as vals.
   Returns a map from nodes to their distance from start."
  [start f]
  (loop [q (pmap/priority-map start 0) r {}]
    (if-let [[v d] (peek q)]
      (let [dist (-> (f v) (remove-keys r) (map-vals (partial + d)))]
        (recur (merge-with min (pop q) dist) (assoc r v d)))
      r)))

(defn surrounding-coordinates [[x y]]
  #{[(inc x) y] [(dec x) y] [x (inc y)] [x (dec y)]})

(defn options-fn [risk-grid]
  (fn [node]
    (let [surrounding (surrounding-coordinates node)]
      (->> surrounding
           (map (juxt identity risk-grid))
           (filter (fn [[_ risk]] (boolean risk)))
           (into {})))))

(defn grid-dim [grid coord-fn]
  (->> (keys grid)
       (map coord-fn)
       (apply max)
       inc))

(defn transpose-risk [modifier risk]
  (let [additional-risk (reduce + modifier)
        total-risk (->> (+ risk additional-risk))
        wrapped-around (inc (mod (dec total-risk) 9))]
    wrapped-around))

(defn transpose-coordinate [[mod-x mod-y] grid-dim-x grid-dim-y [x y]]
  [(+ x (* mod-x grid-dim-x)) (+ y (* mod-y grid-dim-y))])

(defn transpose-fn [grid-dim-x grid-dim-y [coord risk]]
  (fn [modifier]
    (vector
      (transpose-coordinate modifier grid-dim-x grid-dim-y coord)
      (transpose-risk modifier risk))))

(defn parse [lines]
  (let [template-grid (util/parse-grid-map (comp util/to-int str) lines)
        grid-dim-x (grid-dim template-grid first)
        grid-dim-y (grid-dim template-grid second)
        modifiers (for [x (range 5) y (range 5)] [x y])]
    (reduce
      (fn [grid point]
        (->> modifiers
             (map (transpose-fn grid-dim-x grid-dim-y point))
             (into grid)))
      {}
      template-grid)))

(comment "repls tests"

  (def risk-grid (util/parse-grid-map (comp util/to-int str) example))

  (->> (keys risk-grid)
       (map second)
       (apply max))

  ((options-fn {[1 1] 6}) [0 1])

  (-> (dijkstra [0 0] (options-fn risk-grid))
    (get ,,, [9 9]))

  (->> (list 1 2 3 4 5 6 7 8 9 10 11 12 13 14)
       (map (fn [x] (inc (mod (dec x) 5)))))

  (transpose-risk [2 4] 8)
  (transpose-coordinate [1 1] 10 10 [0 0])

  (def expanded-grid (parse example))

  (= 315 (-> (dijkstra [0 0] (options-fn expanded-grid)) (get,,, [49 49])))
  ,)


(defn -main
  "Main function"
  []
  (println (let [risk-grid (->> (util/file->seq "2021/d15.txt") parse)]
             (-> (dijkstra [0 0] (options-fn risk-grid))
                 (get ,,, [499 499])))))