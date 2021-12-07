(ns aoc-2021.day06plus
  (:require [util :as util]
            [clojure.string :as str]))

(def example-input "3,4,3,1,2")

(defn parse-fish-population [input]
  (->> (str/split input #"\,")
       frequencies
       (map (fn [[k v]] [(util/to-int k) v]))
       (into {})))

(defn merge-school-into-population [population days-left size]
  (let [current-school-size (get population days-left 0)
        new-school-size (+ current-school-size size)]
    (assoc population days-left new-school-size)))

(defn tick-one-day [start-population]
  (reduce (fn [population [days-left school-size]]
            (let [new-days-left (dec days-left)]
              (if (< new-days-left 0)                       ;; end of cycle reached
                (merge-school-into-population (merge-school-into-population population 6 school-size) 8 school-size)
                (merge-school-into-population population new-days-left school-size))))
    {}
    start-population))

(defn cycle-for-days [population days]
  (first (take 1 (drop days (iterate tick-one-day population)))))

(defn count-fish [population]
  (reduce + (vals population)))

(comment "repls tests"
  (-> example-input
    parse-fish-population
    (cycle-for-days 256)
    count-fish)
  ,)


(defn -main
  "Main function"
  []
  (println (-> (util/file->str "2021/d06.txt")
               parse-fish-population
               (cycle-for-days 256)
               count-fish)))