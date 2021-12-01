(ns aoc-2021.day01b
  (:require [util :as util]))

(defn calculate-nb-of-sliding-increases [depths]
  (let [window-totals (->> depths
                           (map util/to-int)
                           (partition 3 1)
                           (map (fn [sum] (reduce + 0 sum))))
        window-increases (->> window-totals
                              (partition 2 1)
                              (reduce (fn [total-increases [prev-depth current-depth]]
                                        (if (> current-depth prev-depth)
                                          (inc total-increases)
                                          total-increases))
                                0))]
    window-increases))

(def example-input (list "199" "200" "208" "210" "200" "207" "240" "269" "260" "263"))
(defn test-example []
  (= (calculate-nb-of-sliding-increases example-input) 5))

(def input (util/file->seq "2021/d01a.txt"))

(defn -main
  "Main function"
  []
  (println (calculate-nb-of-sliding-increases input)))