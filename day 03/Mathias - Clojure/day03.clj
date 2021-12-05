(ns aoc-2021.day03
  (:require [util :as util]
            [clojure.string :as str]))

(defn add-on-bits-to-tally [tally nb]
  (map (fn [count digit] (if (= digit \1) (inc count) count)) tally nb))

(defn determine-most-common-per-bit-position [tally nb-count]
  (->> (map (fn [count]
              (if (> count (/ nb-count 2)) "1" "0")) tally)
       (apply str)))

(defn tally-most-common-bits
  "This code assumes no empty numbers list as input"
  ([nbs]
   (let [blank-tally (-> (first nbs) count (repeat 0))]
     (tally-most-common-bits blank-tally 0 nbs)))
  ([tally nb-count [nb & nbs]]
   (let [new-tally (add-on-bits-to-tally tally nb)
         new-count (inc nb-count)]
     (if (seq nbs)
       (recur new-tally new-count nbs)
       (determine-most-common-per-bit-position new-tally new-count)))))

(defn as-binary-number [nb-str]
  (read-string (str "2r" nb-str)))

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

(comment
  (add-on-bits-to-tally [1 2 3 4] "1001")
  (determine-most-common-per-bit-position [3 1 2] 4)
  (tally-most-common-bits example-input)
  (as-binary-number "10110")

  (def gamma (->> example-input
                  tally-most-common-bits
                  as-binary-number))
  (def mask (read-string (->> (cons "2r" (repeat (count "10110") 1))
                              (apply str))))
  (def epsilon (bit-xor gamma mask))
  ,)


(def input (util/file->seq "2021/d03.txt"))

(defn -main
  "Main function"
  []
  (println (let [tally (tally-most-common-bits input)
                 gamma (->> input
                            tally-most-common-bits
                            as-binary-number)
                 flip-mask (->> (cons "2r" (repeat (count tally) 1))
                                (apply str)
                                read-string)
                 epsilon (bit-xor gamma flip-mask)]
             (* gamma epsilon))))