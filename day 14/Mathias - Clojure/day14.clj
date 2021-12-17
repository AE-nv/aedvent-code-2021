(ns aoc-2021.day14
  (:require [util :as util]
            [clojure.string :as str]
            [clojure.set :as set]))

(def example (list
               "NNCB"
               ""
               "CH -> B"
               "HH -> N"
               "CB -> H"
               "NH -> C"
               "HB -> C"
               "HC -> B"
               "HN -> C"
               "NN -> C"
               "BH -> H"
               "NC -> B"
               "NB -> B"
               "BN -> B"
               "BB -> N"
               "BC -> B"
               "CC -> N"
               "CN -> C"))

(def into-monomer (comp keyword str))
(defn split-polymer [polymer-str]
  (into [] (map (fn [x] (into-monomer x)) polymer-str)))

(defn parse-rule [rule-str]
  (let [[_ pair-str binding-str] (re-matches #"([A-Z]{2}) -> ([A-Z])" rule-str)
        pair (split-polymer pair-str)
        binding (into-monomer binding-str)]
    [pair binding]))

(defn parse [lines]
  (let [[raw-polymer raw-rules] (util/split-using str/blank? lines)
        polymer (split-polymer (first raw-polymer))
        rules (->> raw-rules
                   (map parse-rule)
                   (into {}))]
    [polymer rules]))

(defn binding-step [rules polymer [pair & rest]]
  (let [leading-monomer (first pair)
        binding-monomer (rules pair)
        new-chain-so-far (conj polymer leading-monomer binding-monomer)]
    (if (seq rest)
      (recur rules new-chain-so-far rest)
      (conj new-chain-so-far (second pair)))))

(defn n-binding-steps [[polymer rules] n]
  (first (drop n (iterate (fn [x] (binding-step rules [] (partition 2 1 x))) polymer))))

(comment "repls tests"

  (def ex-rules (parse example))
  ex-rules

  (into {} (list (parse-rule "BH -> H")))

  (let [[polymer rules] (parse example)]
    (first (drop 3 (iterate (fn [x] (binding-step rules [] (partition 2 1 x))) polymer))))

  (let [polymer (n-binding-steps (parse example) 10)
        occurrences (vals (frequencies polymer))
        min (apply min occurrences)
        max (apply max occurrences)]
    (- max min))
  ,)


(defn -main
  "Main function"
  []
  (println (let [model (parse (util/file->seq "2021/d14.txt"))
                 polymer (n-binding-steps model 10)
                 occurrences (vals (frequencies polymer))
                 min (apply min occurrences)
                 max (apply max occurrences)]
             (- max min))))