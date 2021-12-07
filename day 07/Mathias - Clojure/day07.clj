(ns aoc-2021.day07
  (:require [util :as util]
            [clojure.string :as str]))

(def example-input "16,1,2,0,4,2,7,1,2,14")

(defn parse-crab-positions [input]
  (->> (str/split input #"\,")
       frequencies
       (map (fn [[k v]] [(util/to-int k) v]))))

(defn position-range [positions]
  (let [min-pos (apply min (map first positions))
        max-pos (apply max (map first positions))]
    (range min-pos (inc max-pos))))

(defn calculate-fuel-for-move-to [positions target]
  (reduce (fn [total-fuel [p total-crabs]]
            (let [distance (util/abs (- p target))
                  fuel-needed (* distance total-crabs)]
              (+ total-fuel fuel-needed)))
    0
    positions))


(comment "repls tests"
  (def ex-positions (parse-crab-positions example-input))
  (calculate-fuel-for-move-to {16 1} 2)

  (apply min (map first ex-positions))
  (apply max (map first ex-positions))

  ;;(apply min-key (fn [x] (calculate-fuel-for-move-to ex-positions x)) (position-range ex-positions))
  (->> (position-range ex-positions)
       (map (fn [target-pos] (calculate-fuel-for-move-to ex-positions target-pos)))
       (apply min))
  ,)


(defn -main
  "Main function"
  []
  (println (let [positions (parse-crab-positions (util/file->str "2021/d07.txt"))]
             (->> (position-range positions)
                  (map (fn [target-pos] (calculate-fuel-for-move-to positions target-pos)))
                  (apply min)))))