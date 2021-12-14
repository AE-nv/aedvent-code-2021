(ns aoc-2021.day13plus
  (:require [util :as util]
            [clojure.string :as str]))

(def example (list
               "6, 10"
               "0, 14"
               "9, 10"
               "0, 3"
               "10, 4"
               "4, 11"
               "6, 0"
               "6, 12"
               "4, 1"
               "0, 13"
               "10, 12"
               "3, 4"
               "3, 0"
               "8, 4"
               "1, 10"
               "2, 14"
               "8, 10"
               "9, 0"
               ""
               "fold along y=7"
               "fold along x=5"))

(defn parse [lines]
  (let [[raw-dots raw-instr] (util/split-using str/blank? lines)
        dots (->> raw-dots
                  (map (comp
                         (fn [[x y]] [(util/to-int x) (util/to-int y)])
                         (fn [line] (str/split line #"\s*,\s*"))))
                  (into #{}))
        instr (->> raw-instr
                   (map (fn [x] (let [[_ dimension value-str] (re-matches #"fold along ([xy])=(\d+)" x)
                                      value (util/to-int value-str)]
                                  (if (= dimension "x") [:left value] [:up value]))))
                   (into []))]
    [dots instr]))

(defn fold [[dim v] [x y]]
  (cond
    (and (= dim :up) (< y v))
    [x y]

    (and (= dim :up) (> y v))
    [x (- (* 2 v) y)]

    (and (= dim :left) (< x v))
    [x y]

    (and (= dim :left) (> x v))
    [(- (* 2 v) x) y]))

(defn do-folds [[initial-dots instructions]]
  (reduce
    (fn [dots instruction]
      (->> (map (fn [dot] (fold instruction dot)) dots)
           (into #{})))
    initial-dots
    instructions))

(defn print-manual [dots]
  (let [max-x (apply max (map first dots))
        max-y (apply max (map second dots))]
    (->> (range (inc max-y))
         (map (fn [y] (->> (filter (fn [[_ dy]] (= y dy)) dots)
                           (map first)
                           (into #{}))))
         (map (fn [dots-on-line] (->> (range (inc max-x))
                                      (map (fn [x] (if (dots-on-line x) "x" " ")))
                                      (apply str))))
         (map (fn [x] (println x) x)))))

(comment "repls tests"
  "transpose everything that has an index larger than that of the fold position"
  (def ex-model (parse example))

  (->> (parse example)
       do-folds
       print-manual)

  (def folded (->> (util/file->seq "2021/d13.txt")
                   parse
                   do-folds))

  (apply max (map second folded))
  ,)


(defn -main
  "Main function"
  []
  (println (->> (util/file->seq "2021/d13.txt")
                parse
                do-folds
                print-manual)))