(ns aoc-2021.day01a
  (:require [util :as util]))

(defn calculate-nb-of-depth-increases [depths]
  (->> depths
       (map util/to-int)
       (partition 2 1)
       (reduce (fn [total-increases [prev-depth current-depth]]
                 (if (> current-depth prev-depth)
                   (inc total-increases)
                   total-increases))
         0)))

(def example-input (list "199" "200" "208" "210" "200" "207" "240" "269" "260" "263"))
(defn test-example []
  (= (calculate-nb-of-depth-increases example-input) 7))

(def input (util/file->seq "2021/d01a.txt"))

(defn -main
  "Main function"
  []
  (println (calculate-nb-of-depth-increases input)))