(ns aoc16.core)

(def demo 
  "class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12")

(defn read-values 
  [s]
  (->> (clojure.string/split-lines s)
       (partition-by #{""})
       (remove #{(list "")})
       (map #(remove #{"your ticket:"} %))
       (map #(remove #{"nearby tickets:"} %))
       (map vec)
       (vec)))

(defn preprocess
  [[rules [your-ticket] nearby-tickets]]
  (let [rules-processed (vec (map #(clojure.string/split % #"(: )|( or )|(-)") rules))
        your-ticket-processed (clojure.string/split your-ticket #",")
        nearby-tickets-processed (vec (map #(clojure.string/split % #",") nearby-tickets))]
    [rules-processed your-ticket-processed nearby-tickets-processed]))

(defn sol-1
  [s]
  (let [[rules _ nearby-tickets] (->> (read-values s)
                                      (preprocess))
        rules-as-fs (->> (map #(subvec % 1) rules)
                         (map (fn [v] (map #(Integer/parseInt %) v)))
                         (map (fn [[a b c d]] (fn [x] (or (<= a x b) (<= c x d))))))
        tickets (flatten (map (fn [v] (map #(Integer/parseInt %) v)) nearby-tickets))]
    (->> (remove (fn [n] (->> (map #(% n) rules-as-fs)
                              (reduce #(or %1 %2))))
                 tickets)
         (reduce +))))

(defn map-to-unique
  [sets]
  (loop [new-sets sets
         uniq (->> (filter #(= 1 (count %)) sets)
                   (first))]
    (let [next-uniq (->> (filter #(= 2 (count %)) new-sets)
                         (map #(remove uniq %))
                         (first)
                         (set))]
      (tap> [uniq next-uniq])
      (if (empty? next-uniq)
        new-sets
        (let [new-new-sets (->> (map #(if (< 1 (count %))
                                        (set (remove uniq %))
                                        %) new-sets))]
          (if (= new-sets new-new-sets)
            new-sets
            (recur new-new-sets next-uniq)))))))
        

(defn sol-2
  [s]
  (let [[rules your-ticket nearby-tickets] (->> (read-values s)
                                                (preprocess))
        rules-as-fs (->> (map #(subvec % 1) rules)
                         (map (fn [v] (map #(Integer/parseInt %) v)))
                         (map (fn [[a b c d]] (fn [x] (or (<= a x b) (<= c x d))))))
        fields (flatten (map (fn [v] (map #(Integer/parseInt %) v)) nearby-tickets))
        invalid-fields (->> (remove (fn [n] (->> (map #(% n) rules-as-fs)
                                                 (reduce #(or %1 %2))))
                                    fields)
                            (set))
        valid-tickets (->> (map (fn [ticket] (map #(Integer/parseInt %) ticket)) (conj nearby-tickets your-ticket))
                           (filter #(empty? (clojure.set/intersection invalid-fields (set %)))))
        valid-fields-by-index (->> (range (count (first valid-tickets)))
                                   (map (fn [i] (map (fn [ticket] (nth ticket i)) valid-tickets))))]
    (->> (map (fn [fields] (->> (filter (fn [f] (->> (map f fields)
                                                     (reduce #(and %1 %2))))
                                        rules-as-fs)
                                (map (fn [f] (.indexOf rules-as-fs f)))))
              valid-fields-by-index)
         (map set)
         (map-to-unique)
         ((fn [sets] (map #(vector % (first (nth sets %))) (range (count sets)))))
         (filter (fn [[i ifn]] (> 6 ifn)))
         (map first)
         (map #(nth your-ticket %))
         (map #(Integer/parseInt %))
         (reduce *))))
      

(comment
  
  (sol-1 demo)
  (sol-1 (slurp "resources/input.txt")) ;=> 23925
  
  (sol-2 (slurp "resources/input.txt")) ;=> 964373157673
   
  
 ,)
