(ns aoc-2021.day08
  (:require [util :as util]
            [clojure.string :as str]))

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


(defn parse-display [line]
  (let [[_ raw-output] (str/split line #" \| ")
        output (->> (str/split raw-output #"\s")
                    (map (fn [digit]
                           (into #{} (map (comp keyword str) digit)))))]
    output))

(def nb-by-segment-count {2 [1] 3 [7] 4 [4] 5 [2 3 5] 6 [0 6 9] 7 [8]})
(defn eliminate-by-segment-count [digit]
  (nb-by-segment-count (count digit)))

(defn keep-unique-digits [digit]
  (= (count (eliminate-by-segment-count digit)) 1))

(comment "repls tests"
  (parse-display "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe")
  (eliminate-by-segment-count  #{:e :g :c :b})

  (->> example-input
       (mapcat parse-display)
       (filter keep-unique-digits)
       count)
  ,)


(defn -main
  "Main function"
  []
  (println (->> (util/file->seq "2021/d08.txt")
                (mapcat parse-display)
                (filter keep-unique-digits)
                count)))