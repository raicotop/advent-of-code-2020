(ns aoc7.core
  (:require clojure.set))

(defn row-to-map
  [row]
  (->> (drop 2 row)
       (partition 2)
       (map reverse)
       (map #(vector (first %) (Integer/parseInt (second %))))
       (map #(apply hash-map %))
       (reduce into)
       (hash-map (second row))))

(defn bags-containing
  [bag bags]
  (let [set-bags (map #(hash-map (first %) (set (keys (second %)))) bags)]
   (loop [known-bags #{bag}]
    (let [new-bags (->> set-bags
                        (remove #(empty? (clojure.set/intersection (first (vals %)) known-bags)))
                        (map keys)
                        (reduce concat)
                        (set))
          known-bags-new (clojure.set/union known-bags new-bags)]
      (if (empty? (clojure.set/difference known-bags-new known-bags))
        known-bags
        (recur known-bags-new))))))
        
(defn sol
  [s]
  (->> (clojure.string/split-lines s)
       (map #(apply str (remove #{\, \.} %)))
       (map #(clojure.string/split % #" "))
       (map #(remove #{"bag" "bags" "contain" "no" "other"} %))
       (map #(conj % "1"))
       (map (fn [words] (->> (range (-> words (count) (/ 3) (* 2)))
                             (map #(if (odd? %)
                                     (str (nth words (+ 1 (* 3 (/ (dec %) 2)))) 
                                          " " 
                                          (nth words (+ 2 (* 3 (/ (dec %) 2)))))
                                     (nth words (* 3 (/ % 2))))))))
       (map row-to-map)
       (into {})))         

(defn sol1
  [s]
  (->> (sol s)
       (bags-containing "shiny gold")
       (count)
       (dec)))

(defn subbags-count
  [m]
  (loop [bags-processed (->> (keys m)
                             (filter #(empty? (m %)))
                             (map #(hash-map % 1))
                             (reduce into))
         bags-to-process (apply dissoc m (keys bags-processed))]
    (if (empty? bags-to-process)
      bags-processed
      (let [bags-to-add (->> (keys bags-to-process)
                             (filter #(clojure.set/subset?
                                       (set (keys (bags-to-process %)))
                                       (set (keys bags-processed)))))
            bags-processed-new (->> bags-to-add
                                    (map #(hash-map
                                           %
                                           (->> (map (fn [[a b]] (* (bags-processed a) b)) (m %))
                                                (reduce + 1))))
                                    (reduce into)
                                    (merge bags-processed))
            bags-to-process-new (apply dissoc bags-to-process bags-to-add)]
        (recur bags-processed-new bags-to-process-new)))))

(defn sol2
  [s]
  (->> (sol s)
       (subbags-count)
       (filter #(= "shiny gold" (first %)))
       (map second)
       (first)
       (dec)))

(comment
  (sol1 (slurp "resources/input.txt")) ;=> 233
  (sol2 (slurp "resources/input.txt")));=> 421550