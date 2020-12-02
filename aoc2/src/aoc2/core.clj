(ns aoc2.core)

(defn valid1?
  [min max ch chars]
  (<= min
      (get (frequencies chars) ch 0)
      max))

(defn valid2?
  [i1 i2 ch chars]
  (->> (map #(nth chars (dec %)) [i1 i2])
       (filter #(= ch %))
       (#(= 1 (count %)))))

(defn parse-line
  [line]
  (let [[min max ch chars] (re-seq #"\w+" line)]
    [(read-string min)
     (read-string max)
     (first ch)
     chars]))

(defn sol
  [rules valid?]
  (->> (clojure.string/split-lines rules)
       (map parse-line)
       (filter #(apply valid? %))
       (count)))

(defn sol1
  [rules]
  (sol rules valid1?))

(defn sol2
  [rules]
  (sol rules valid2?))

(comment
  (sol1 (slurp "input.txt")) ;=> 600
  (sol2 (slurp "input.txt"))) ;=> 245)
