(ns aoc-2021.day03plus
  (:require [util :as util]))

(def example-input (list
                     "00100"
                     "11110"
                     "10110"
                     "10111"
                     "10101"
                     "01111"
                     "00111"
                     "11100"
                     "10000"
                     "11001"
                     "00010"
                     "01010"))

(defn bit-on-at-position? [binary pos]
  (bit-test binary pos))
(defn bit-off-at-position? [binary pos]
  (not (bit-on-at-position? binary pos)))

(defn filter-bit-on [position]
  (fn [binary-nbs]
    (filter (fn [nb] (bit-on-at-position? nb position)) binary-nbs)))
(defn filter-bit-off [position]
  (fn [binary-nbs]
    (filter (fn [nb] (bit-off-at-position? nb position)) binary-nbs)))

(defn split-by-bit [position numbers]
  ((juxt (filter-bit-on position) (filter-bit-off position)) numbers))

(defn eliminate [position comparator-fn numbers]
  (if (or (< position 0) (= (count numbers) 1))
    (first numbers)
    (let [[ones zeros] (split-by-bit position numbers)]
      (if (comparator-fn (count ones) (count zeros))
        (recur (dec position) comparator-fn ones)
        (recur (dec position) comparator-fn zeros)))))

(defn as-binary-number [nb]
  (read-string (str "2r" nb)))
(def example-numbers (map as-binary-number example-input))

(comment "repl tests"
  (bit-test 2r1110 0)
  (bit-on-at-position? 2r1010 1)
  (bit-off-at-position? 2r1010 1)

  (def split-by-bit-one (juxt (filter-bit-on 1) (filter-bit-off 1)))
  (->> (list 2r1111 2r0010 2r0000 2r1101)
       (split-by-bit-one))
  (eliminate (dec (count example-numbers)) example-numbers)

  "debugging"
  (split-by-bit 4 example-numbers)
  (split-by-bit 3 '(30 22 23 21 28 16 25))
  (split-by-bit 2 '(22 23 21 16))
  (split-by-bit 1 '(22 23 21))
  (split-by-bit 0 '(22 23))

  (= 23 (eliminate 4 >= example-numbers))
  (= 10 (eliminate 4 < example-numbers))
  ,)



(def input (util/file->seq "2021/d03.txt"))


(defn -main
  "Main function"
  []
  (println (let [max-bit-position (dec (count (first input)))
                 binary-numbers (map as-binary-number input)
                 O2-gen-rating (eliminate max-bit-position >= binary-numbers)
                 CO2-scrub-rating (eliminate max-bit-position < binary-numbers)]
             (* O2-gen-rating CO2-scrub-rating))))