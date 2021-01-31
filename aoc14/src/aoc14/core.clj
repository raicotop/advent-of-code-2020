(ns aoc14.core)

(def demo 
  "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
mem[8] = 11
mem[7] = 101
mem[8] = 0")

(defn parse-input
  [s]
  (->> (clojure.string/split-lines s)
       (map #(if (= "mask" (apply str (take 4 %)))
               (apply str (take-last 36 %))
               (let [[l r] (clojure.string/split % #"=")]
                 [(Long/parseLong (apply str (drop-last (count "] ") (drop (count "mem[") l))))
                  (Long/parseLong (apply str (drop (count " ") r)))])))))
       
(defn apply-mask-1
  [value mask]
  (let [value-binary (Long/toBinaryString value)
        value-binary-with-leading-zeros (str (apply str (take (- 36 (count value-binary)) (repeat "0")))
                                         value-binary)]
    (->> (range 36)
         (map #(let [mask-bit (nth mask %)
                     value-bit (nth value-binary-with-leading-zeros %)]
                 (condp = mask-bit
                   \0 \0
                   \1 \1
                   \X value-bit)))
         (apply str)
         (#(Long/parseLong % 2)))))
                   
(defn update-memory-1
  [memory [address value] mask]
  (assoc memory address (apply-mask-1 value mask)))

(defn expand-address
  [address]
  (loop [to-process [address]
         processed []]
    (if (empty? to-process)
      processed
      (let [adr (first to-process)
            x-pos (.indexOf adr "X")]
        (if (= -1 x-pos)
          (recur (rest to-process) (conj processed adr))
          (let [expand (fn [bit] (str (subs adr 0 x-pos) bit (subs adr (inc x-pos))))
                expanded-0 (expand "0")
                expanded-1 (expand "1")]
            (recur (conj (rest to-process) expanded-0 expanded-1) processed)))))))

(defn apply-mask-2
  [address mask]
  (tap> [address mask])
  (let [value-binary (Long/toBinaryString address)
        value-binary-with-leading-zeros (str (apply str (take (- 36 (count value-binary)) (repeat "0")))
                                             value-binary)]
    (->> (range 36)
         (map #(let [mask-bit (nth mask %)
                     value-bit (nth value-binary-with-leading-zeros %)]
                 (condp = mask-bit
                   \0 value-bit
                   \1 \1
                   \X \X)))
         (apply str))))
        

(defn update-memory-2
  [memory [address value] mask]
  (let [adr (apply-mask-2 address mask)
        adrs (expand-address adr)]
    (->> (map #(vector % value) adrs)
         (flatten)
         (apply assoc memory))))

(defn sol
  [s update-memory]
  (loop [instructions (parse-input s)
         mask nil
         memory {}]
    (if (empty? instructions)
      (reduce + (vals memory))
      (let [instruction (first instructions)]
        (if (= 36 (count instruction))
          (recur (rest instructions) instruction memory)
          (recur (rest instructions) 
                 mask 
                 (update-memory memory instruction mask)))))))
      

(comment
  (sol demo update-memory-1)
  (sol (slurp "resources/input.txt") update-memory-1) ;=> 13476250121721
  
  (sol (slurp "resources/input.txt") update-memory-2) ;=> 4463708436768
  ,)
