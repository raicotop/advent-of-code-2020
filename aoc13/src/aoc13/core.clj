(ns aoc13.core)

(def demo
  "939
7,13,x,x,59,x,31,19")

(defn filter-input-1
  [time buses]
  (let [t (Integer/parseInt time)
        bs (->> (remove #{"x"} buses)
                (map #(Integer/parseInt %)))]
    [t bs]))

(defn read-input
  [s]
  (->> (clojure.string/split-lines s)
       (#(vector (first %) (clojure.string/split (second %) #",")))))

(defn sol-1
  [s]
  (let [in (read-input s)
        [time buses] (apply filter-input-1 in)]
    (->> (map #(vector % (- % (mod time %))) buses)
         (reduce (fn [x y] (if (< (second x) (second y))
                             x
                             y)))
         (reduce *))))

(defn filter-input-2
  [_ buses]
  (->> (range (count buses))
       (map #(vector % (nth buses %)))
       (remove #(= "x" (second %)))
       (map #(let [m (Integer/parseInt (second %))]
                (vector (mod (- m (first %)) m) m)))))
       
(defn lcm
  [a b]
  (/ (* a b) (int (.gcd (biginteger a) (biginteger b)))))

(defn red
  [[a1 m1] [a2 m2]]
  (let [m (lcm m1 m2)
        a (loop [x a1]
            (if (= 0 (mod (- x a2) m2))
              x
              (recur (+ x m1))))]
    [a m]))

(defn sol-2
  [s]
  (let [in (read-input s)
        buses (apply filter-input-2 in)]
    (reduce red buses)))

(comment
  (sol-1 demo)
  (sol-1 (slurp "resources/input.txt")) ;=> 410
  
  (sol-2 demo)
  (sol-2 (slurp "resources/input.txt")) ;=> [600691418730595 1090937521514009]
 ,)


