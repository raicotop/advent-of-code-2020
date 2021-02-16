(ns aoc19.core)

(def demo 
  "0: 4 6
1: 2 3 | 3 2
2: 4 4 | 5 5
3: 4 5 | 5 4
4: a
5: b
6: 1 5

ababbb
bababa
abbbab
aaabbb
aaaabbb")

(defn make-rules
  [rules-as-strings]
  (loop [rules-to-process rules-as-strings
         rules-processed {}]
    (if (empty? rules-to-process)
      rules-processed
      (let [r (first rules-to-process)
            parts (clojure.string/split r #" ")
            n (->> (first parts)
                   (drop-last)
                   (apply str))
            rules-processed' (merge-with clojure.set/union rules-processed (condp = (count parts)
                                                                             2 {(second parts) #{n}}
                                                                             3 {[(nth parts 1) (nth parts 2)] #{n}}
                                                                             6 {[(nth parts 1) (nth parts 2)] #{n}
                                                                                [(nth parts 4) (nth parts 5)] #{n}}
                                                                             9 {[(nth parts 1) (nth parts 2)] #{n}
                                                                                [(nth parts 4) (nth parts 5)] #{n}
                                                                                [(nth parts 7) (nth parts 8)] #{n}}
                                                                             12 {[(nth parts 1) (nth parts 2)] #{n}
                                                                                 [(nth parts 4) (nth parts 5)] #{n}
                                                                                 [(nth parts 7) (nth parts 8)] #{n}
                                                                                 [(nth parts 10) (nth parts 11)] #{n}}))]
        (recur (rest rules-to-process) rules-processed')))))
        
(defn cyk-gen-combs
  [[x y]]
  (for [x' (range x)]
    [[x' y] [(- (dec x) x') (+ (inc y) x')]]))

(defn cyk [word rules]
  (loop [table [(map rules (map str word))]]
    (if (= 1 (count (last table)))
      table
      (let [x (count table)
            new-row (->> (for [y (range (- (count word) x))]
                           [x y])
                         (map cyk-gen-combs)
                         (map (fn [field-combs]
                                (->> (map (fn [[[x0 y0] [x1 y1]]]
                                           (->> (for [s0 (nth (nth table x0) y0)
                                                      s1 (nth (nth table x1) y1)]
                                                  [s0 s1])
                                                (map #(get rules % #{}))
                                                (reduce clojure.set/union)))                                       
                                      field-combs))))                         
                         (map #(reduce clojure.set/union %)))]
        (recur (conj table new-row))))))    

(defn parse
  [s]
  (->> (clojure.string/split-lines s)
       (partition-by #{""})
       (remove #{(list "")})
       (#(vector (make-rules (first %)) (vec (second %))))))

(defn sol
  [in]
  (let [[rules words] (parse in)]
    (->> (map #(cyk % rules) words)
         (map #(contains? (last (last %)) "0"))
         (map {false 0 true 1})
         (reduce +))))

(comment 
  
  (sol demo)

  (sol (slurp "resources/input.txt")) ;=> 291 
  
  (sol (slurp "resources/input2.txt")) ;=> 409

  ,)
