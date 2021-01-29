(defproject aoc11 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :profiles {:reveal {:dependencies [[vlaaad/reveal "1.2.185"]]
                      :repl-options {:nrepl-middleware ["vlaaad.reveal.nrepl/middleware"]}}})

