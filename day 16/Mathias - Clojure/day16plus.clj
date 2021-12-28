(ns aoc-2021.day16plus
  (:require [util :as util]))

(def example-1 "D2FE28")

(def hex->bits {\0 [0 0 0 0]
                \1 [0 0 0 1]
                \2 [0 0 1 0]
                \3 [0 0 1 1]
                \4 [0 1 0 0]
                \5 [0 1 0 1]
                \6 [0 1 1 0]
                \7 [0 1 1 1]
                \8 [1 0 0 0]
                \9 [1 0 0 1]
                \A [1 0 1 0]
                \B [1 0 1 1]
                \C [1 1 0 0]
                \D [1 1 0 1]
                \E [1 1 1 0]
                \F [1 1 1 1]})

(defn times-two [x] (* 2 x))
(defn bits->number [bits]
  (->> (map * (reverse bits) (iterate times-two 1))
       (reduce +)))

(defn read-header [[v1 v2 v3 t1 t2 t3 & rest]]
  (vector
    {:version (bits->number [v1 v2 v3]) :type (bits->number [t1 t2 t3])}
    rest))

(defn read-literal [bits]
  (loop [significant-bits [] length 0 [prefix b1 b2 b3 b4 & rest] bits]
    (let [new-length (+ length 5)
          new-bits (into significant-bits [b1 b2 b3 b4])]
      (if (= prefix 1)
        (recur new-bits new-length rest)
        [(bits->number new-bits) new-length rest]))))

(defn read-operator [[mode & bits]]
  (if (= mode 0)
    [:length-in-bits (bits->number (take 15 bits)) (drop 15 bits) (inc 15)]
    [:nb-sub-packets (bits->number (take 11 bits)) (drop 11 bits) (inc 11)]))

(declare continuation-on-length-fn)
(declare continuation-on-subcount-fn)
(defn read-packet [bits k]
  "tail recursive through continuation"
  (let [[header next-bits] (read-header bits)
        {type :type}       header]
    (if (= type 4)
      (let [[value length bits-after-literal] (read-literal next-bits)]
        (k [(assoc header
              :length (+ length 6)
              :value value)
            bits-after-literal]))
      (let [[content-type nb next-bits op-bit-length] (read-operator next-bits)]
        (if (= content-type :length-in-bits)
          (read-packet next-bits (continuation-on-length-fn header k [] nb op-bit-length))
          (read-packet next-bits (continuation-on-subcount-fn header k [] nb op-bit-length))))
      )))

(defn read-packets [bits]
  (let [[root-packet left-over-bits] (read-packet bits identity)]
    root-packet))

(defn continuation-on-length-fn [header k sub-packets nb-bits-still-needed op-bit-length]
  (fn [[sub-packet next-bits]]
    (let [new-subpackets (conj sub-packets sub-packet)
          nb-bits-needed-after-this (- nb-bits-still-needed (sub-packet :length))]
      (if (= nb-bits-needed-after-this 0)
        (k [(assoc header
              :length (reduce + (+ 6 op-bit-length) (map :length new-subpackets))
              :operands new-subpackets)
            next-bits])
        (read-packet next-bits (continuation-on-length-fn header k new-subpackets nb-bits-needed-after-this op-bit-length))))))

(defn continuation-on-subcount-fn [header k sub-packets nb-packets-still-needed op-bit-length]
  (fn [[sub-packet next-bits]]
    (let [new-subpackets (conj sub-packets sub-packet)
          nb-packets-needed-after-this (dec nb-packets-still-needed)]
      (if (= nb-packets-needed-after-this 0)
        (k [(assoc header
              :length (reduce + (+ 6 op-bit-length) (map :length new-subpackets))
              :operands new-subpackets)
            next-bits])
        (read-packet next-bits (continuation-on-subcount-fn header k new-subpackets nb-packets-needed-after-this op-bit-length))))))

(defn boolean->int [bool]
  (if (= bool true) 1 0))

(defn execute [{type :type value :value operands :operands}]
  (let [resolved (map execute operands)]
    (condp = type
      0 (reduce + resolved)
      1 (reduce * resolved)
      2 (apply min resolved)
      3 (apply max resolved)
      4 value
      5 (boolean->int (apply > resolved))
      6 (boolean->int (apply < resolved))
      7 (boolean->int (apply = resolved)))))

(comment "repls tests"
  (apply str (mapcat hex->bits example-1))

  (def bits-example-1 (mapcat hex->bits example-1))

  (bits->number [1 0 0 0])
  (read-header bits-example-1)
  (read-packet bits-example-1 identity)
  (read-literal [1 0 1 1 1 1 1 1 1 0 0 0 1 0 1 0 0 0])

  (= (read-packets bits-example-1) {:version 4, :type 4, :length 21, :value 2021})

  (def bits-length-op-with-literals (mapcat hex->bits "38006F45291200"))
  (= (read-packets bits-length-op-with-literals)
    {:version  1,
     :type     6,
     :length   33,
     :operands [
                {:version 6, :type 4, :length 11, :value 10}
                {:version 2, :type 4, :length 16, :value 20}]})

  (def bits-subcount-op-with-literals (mapcat hex->bits "EE00D40C823060"))
  (= (read-packets bits-subcount-op-with-literals)
    {:version 7,
     :type 3,
     :length 39,
     :operands [{:version 2, :type 4, :length 11, :value 1}
                {:version 4, :type 4, :length 11, :value 2}
                {:version 1, :type 4, :length 11, :value 3}]})


  (->> (read-packets (mapcat hex->bits "C200B40A82"))
       execute)
  (->> (read-packets (mapcat hex->bits "04005AC33890"))
       execute)
  (->> (read-packets (mapcat hex->bits "880086C3E88112"))
       execute)
  (->> (read-packets (mapcat hex->bits "CE00C43D881120"))
       execute)
  (->> (read-packets (mapcat hex->bits "D8005AC2A8F0"))
       execute)
  (->> (read-packets (mapcat hex->bits "F600BC2D8F"))
       execute)
  (->> (read-packets (mapcat hex->bits "9C005AC2F8F0"))
       execute)
  (->> (read-packets (mapcat hex->bits "9C0141080250320F1802104A08"))
       execute)
  ,)


(defn -main
  "Main function"
  []
  (println (->> (util/file->str "2021/d16.txt")
                (mapcat hex->bits)
                read-packets
                execute)))