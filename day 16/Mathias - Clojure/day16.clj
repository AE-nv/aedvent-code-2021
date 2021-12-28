(ns aoc-2021.day16
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

(defn version-sum [root-packet]
  (->> (tree-seq (fn [x] (seq (x :operands))) :operands root-packet)
       (map :version)
       (reduce +)))

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


  (->> (read-packets (mapcat hex->bits "8A004A801A8002F478"))
       version-sum)
  (->> (read-packets (mapcat hex->bits "620080001611562C8802118E34"))
       version-sum)
  (->> (read-packets (mapcat hex->bits "C0015000016115A2E0802F182340"))
       version-sum)
  (->> (read-packets (mapcat hex->bits "A0016C880162017C3686B18A3D4780"))
       version-sum)
  ,)


(defn -main
  "Main function"
  []
  (println (->> (util/file->str "2021/d16.txt")
                (mapcat hex->bits)
                read-packets
                version-sum)))