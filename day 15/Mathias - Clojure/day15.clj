(ns aoc-2021.day15
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

(comment "repls tests"

  (def risk-grid (util/parse-grid-map (comp util/to-int str) example))

  ((options-fn {[1 1] 6}) [0 1])

  (-> (dijkstra [0 0] (options-fn risk-grid))
    (get ,,, [9 9]))
  ,)


(defn -main
  "Main function"
  []
  (println (let [risk-grid (->> (util/file->seq "2021/d15.txt")
                                (util/parse-grid-map (comp util/to-int str)))]
             (-> (dijkstra [0 0] (options-fn risk-grid))
                 (get ,,, [99 99])))))