(ns aoc-2021.day08plus
  (:require [util :as util]
            [clojure.string :as str]
            [clojure.set :as set]))

(def example-input (list
                     "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe"
                     "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc"
                     "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg"
                     "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb"
                     "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea"
                     "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb"
                     "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe"
                     "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef"
                     "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb"
                     "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce"))

(defn segment-sets [segments]
  (->> (str/split segments #"\s")
       (map (fn [digit]
              (into #{} (map (comp keyword str) digit))))))
(defn parse [line]
  (let [[signal-patterns output] (str/split line #" \| ")]
    [(segment-sets signal-patterns) (segment-sets output)]))

(defn has-length? [x length] (= (count x) length))
(defn has-length?-fn [length] (fn [x] (has-length? x length)))
(defn find-first [pred col] (first (filter pred col)))
(defn digit-decoder [signal-patterns]
  "takes a collection of signal patterns and creates a decoder function based on the knowledge gained"
  (let [one-pattern (find-first (has-length?-fn 2) signal-patterns)
        four-pattern (find-first (has-length?-fn 4) signal-patterns)
        seven-pattern (find-first (has-length?-fn 3) signal-patterns)
        eight-pattern (find-first (has-length?-fn 7) signal-patterns)
        bd-pattern (set/difference four-pattern seven-pattern)
        three? (fn [x] (and (has-length? x 5) (set/subset? one-pattern x)))
        five? (fn [x] (and (has-length? x 5) (set/subset? bd-pattern x)))
        two? (fn [x] (and (has-length? x 5) (not (three? x)) (not (five? x))))
        zero? (fn [x] (and (has-length? x 6) (not (set/subset? bd-pattern x))))
        six? (fn [x] (and (has-length? x 6) (not (set/subset? one-pattern x))))
        nine? (fn [x] (and (has-length? x 6) (not (zero? x)) (not (six? x))))]

    (fn [digit-output]
      (cond
        (= digit-output one-pattern) 1
        (= digit-output four-pattern) 4
        (= digit-output seven-pattern) 7
        (= digit-output eight-pattern) 8
        (three? digit-output) 3
        (five? digit-output) 5
        (two? digit-output) 2
        (zero? digit-output) 0
        (six? digit-output) 6
        (nine? digit-output) 9))))

(defn decode [[patterns outputs]]
  (let [decoder (digit-decoder patterns)]
    (map decoder outputs)))

(defn digits->number [digits]
  (util/to-int (apply str digits)))

(comment "repls tests"
  (parse "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe")
  (eliminate-by-segment-count  #{:e :g :c :b})

  (def decoder (->> example-input
                    (map parse)
                    first
                    first
                    digit-decoder))

  (= 3 (decoder #{:f :e :c :d :b}))
  (= 5 (decoder #{:f :e :c :d :g}))
  (= 2 (decoder #{:f :a :c :d :b}))

  (let [[patterns outputs] (->> example-input (map parse) first)]
    (decode [patterns outputs]))

  (->> example-input
       (map parse)
       (map decode)
       (map digits->number)
       (reduce +))
  ,)


(defn -main
  "Main function"
  []
  (println (->> (util/file->seq "2021/d08.txt")
                (map parse)
                (map decode)
                (map digits->number)
                (reduce +))))