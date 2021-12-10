(ns aoc-2021.day09plus
  (:require [util :as util]
            [clojure.set :as set]))

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
                (conj minima coord)
                minima)))
    '()
    grid))

(defn surrounding-part-of-basin [grid coord]
  (let [surrounding (surrounding-coordinates coord)]
    (->> (map (juxt identity grid) surrounding)
         (filter (fn [[_ value]] (and value (< value 9))))
         (map first))
    ))

(defn expand-basin [grid basin-so-far edge]
  (let [all-surrounding (->> (mapcat (fn [x] (surrounding-part-of-basin grid x)) edge)
                             (into #{}))
        new-edge (set/difference all-surrounding basin-so-far)
        new-basin (into basin-so-far new-edge)]
    (if (seq new-edge)
      (recur grid new-basin new-edge)
      new-basin)))

(defn chart-basin [minimum grid]
  (expand-basin grid #{minimum} #{minimum}))

(comment "repls tests"
  (def ex-grid (parse example-input))

  (surrounding-still-part-of-basin ex-grid [0 1])
  (expand-basin ex-grid #{[0 1]} )
  (chart-basin [0 1] ex-grid)


  (->> (scan-for-minima ex-grid)
       (map (fn [m] (chart-basin m ex-grid)))
       (map count)
       (sort (comp - compare))
       (take 3)
       (reduce *))

  ,)


(defn -main
  "Main function"
  []
  (println (let [grid (parse (util/file->seq "2021/d09.txt"))]
             (->> (scan-for-minima grid)
                  (map (fn [m] (chart-basin m grid)))
                  (map count)
                  (sort (comp - compare))
                  (take 3)
                  (reduce *)))))