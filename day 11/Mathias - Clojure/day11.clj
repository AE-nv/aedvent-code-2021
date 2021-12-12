(ns aoc-2021.day11
  (:require [util :as util]))

(def example-input (list
                     "5483143223"
                     "2745854711"
                     "5264556173"
                     "6141336146"
                     "6357385478"
                     "4167524645"
                     "2176841721"
                     "6882881134"
                     "4846848554"
                     "5283751526"))

(defn char->int [nb-char]
  (util/to-int (str nb-char)))

(defn parse [grid-lines]
  (util/parse-grid-map char->int grid-lines))

(defn flash? [energy-level]
  (> energy-level 9))

(defn touched-by-flash [[row col :as center]]
  (for [x [(dec row) row (inc row)]
        y [(dec col) col (inc col)]
        :when (not= center [x y])]
    [x y]))

(defn step
  ([[grid flash-count]]
   "convenience arity to start the stepping recursion"
   (let [[grid-post-step flashes-during-step] (step grid (keys grid) #{})]
     [grid-post-step (+ flash-count flashes-during-step)]))
  ([grid [coord & rest-to-energize] energized]
   (let [nothing-to-energize (not coord)
         new-energy-level (some-> (grid coord) inc)]
     (cond
       nothing-to-energize
       [grid (count energized)]

       ;; point doesn't have squid or already energized
       (or (not new-energy-level) (energized coord))
       (recur grid rest-to-energize energized)

       (flash? new-energy-level)
       (let [new-grid (assoc grid coord 0)
             affected-by-flash (->> (touched-by-flash coord)
                                    (filter (fn [x] (not (energized x)))))
             to-energize (into rest-to-energize affected-by-flash)
             new-energized (conj energized coord)]
         (recur new-grid to-energize new-energized))

       :else
       (let [new-grid (assoc grid coord new-energy-level)]
         (recur new-grid rest-to-energize energized))))))

(defn step-n-times [grid n]
  (first (drop n (iterate step [grid 0]))))

(comment "repls tests"
  "everyone goes on queue
   for each in the queue +1 and see if it passes 10
   if so add surrounding to the stack so they will be upped again

   Need to track who flashed already? will be simplest just reset those to 0 0's dont flash anymore"
  ((parse example-input) [9 9])

  (def mini-example (list
                      "11111"
                      "19991"
                      "19191"
                      "19991"
                      "11111"))
  (def mini-grid (parse mini-example))
  (step [mini-grid 0])
  (sort (first (step-n-times mini-grid 2)))

  (step-n-times (parse example-input) 100)

  ,)


(defn -main
  "Main function"
  []
  (println (step-n-times (parse (util/file->seq "2021/d11.txt")) 100)))