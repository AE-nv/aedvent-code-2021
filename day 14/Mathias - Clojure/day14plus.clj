(ns aoc-2021.day14plus
  (:require [util :as util]
            [clojure.string :as str]))

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

(defn deconstruct-polymer-into-map [polymer-str]
  (->> polymer-str
       (map (fn [x] (into-monomer x)))
       (partition 2 1)
       (map vec)
       frequencies))

(defn parse-rule [rule-str]
  (let [[_ pair-str binding-str] (re-matches #"([A-Z]{2}) -> ([A-Z])" rule-str)
        [a b :as pair] (split-polymer pair-str)
        binding (into-monomer binding-str)]
    [pair (list [a binding] [binding b])]))

(defn parse [lines]
  (let [[raw-polymer raw-rules] (util/split-using str/blank? lines)
        polymer (deconstruct-polymer-into-map (first raw-polymer))
        rules (->> raw-rules
                   (map parse-rule)
                   (into {}))]
    [polymer rules]))

(defn binding-step [rules polymer-map]
  (reduce
    (fn [polymer-map [[a b] freq]]
      (let [new-polymers-map (->> (rules [a b])
                                  (map (fn [p] [p freq]))
                                  (into {}))]
        (merge-with + polymer-map new-polymers-map)))
    {}
    polymer-map))

(defn n-binding-steps [[polymer rules] n]
  (first (drop n (iterate (fn [x] (binding-step rules x)) polymer))))

(defn correct-first-and-last-monomer-part-of-only-one-pair [freqs-based-on-pairs first-monomer last-monomer]
  (let [first-monomer-freq (freqs-based-on-pairs first-monomer)
        last-monomer-freq (freqs-based-on-pairs last-monomer)]
    (assoc freqs-based-on-pairs
      first-monomer (inc first-monomer-freq)
      last-monomer (inc last-monomer-freq))))

(defn pair-frequencies->monomer-frequenties [polymer first-monomer last-monomer]
  (let [freqs-based-on-pairs (reduce
                               (fn [monomer-frequencies [[a b] freq-of-pair]]
                                 (let [current-freq-a (get monomer-frequencies a 0)
                                       current-freq-b (get monomer-frequencies b 0)]
                                   (if (not= a b)
                                     (assoc monomer-frequencies
                                       a (+ current-freq-a freq-of-pair)
                                       b (+ current-freq-b freq-of-pair))
                                     (assoc monomer-frequencies a (+ current-freq-a (* 2 freq-of-pair))))))
                               {}
                               polymer)
        double-monomer-frequenties (correct-first-and-last-monomer-part-of-only-one-pair
                                     freqs-based-on-pairs first-monomer last-monomer)]
    (->> double-monomer-frequenties
         (map (fn [[m freq]] [m (quot freq 2)]))
         (into {}))))

(comment "repls tests"

  (def ex-rules (parse example))
  ex-rules

  (deconstruct-polymer-into-map "NNCB")

  (let [[polymer rules] (parse example)]
    (binding-step rules polymer))

  (= (n-binding-steps (parse example) 4)
    (deconstruct-polymer-into-map "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB"))

  (frequencies (split-polymer "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB"))
  (frequencies (split-polymer "NBCCNBBBCBHCB"))
  (deconstruct-polymer-into-map "NBCCNBBBCBHCB")
  (pair-frequencies->monomer-frequenties (deconstruct-polymer-into-map "NBCCNBBBCBHCB") :N :B)

  ,)


(defn -main
  "Main function"
  []
  (println (let [model (parse (util/file->seq "2021/d14.txt"))
                 polymer (n-binding-steps model 40)
                 occurrences (vals (pair-frequencies->monomer-frequenties polymer :O :B))
                 min (apply min occurrences)
                 max (apply max occurrences)]
             (- max min))))