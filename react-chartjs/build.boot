(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.1" :scope "test"]
		  [cljsjs/chartjs "2.0.1-0"]
		  [cljsjs/react "0.14.3-0"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "0.7.3")
(def +version+ (str +lib-version+ "-0"))

(task-options!
  pom  {:project     'cljsjs/react-chartjs
        :version     +version+
        :description "rich interactive react charting components using chart.js"
        :url         "https://github.com/jhudson8/react-chartjs"
        :license     {"MIT" "http://opensource.org/licenses/MIT"}
        :scm         {:url "https://github.com/cljsjs/packages"}})

(defn file-ver [file]
  (str "https://raw.githubusercontent.com/jhudson8/react-chartjs/" +lib-version+ "/dist/" file))

(deftask package []
  (comp
   (download :url (file-ver "react-chartjs.js"))
   (download :url (file-ver "react-chartjs.min.js"))
   (sift :move {#"react-chartjs.js" "cljsjs/react-chartjs/development/react-chartjs.inc.js"
                #"react-chartjs.min.js" "cljsjs/react-chartjs/production/react-chartjs.min.inc.js"})
   (sift :include #{#"^cljsjs"})
   (deps-cljs :name "cljsjs.react-chartjs"
              :requires ["cljsjs.chartjs" "cljsjs.react"])
   (pom)
   (jar)))
