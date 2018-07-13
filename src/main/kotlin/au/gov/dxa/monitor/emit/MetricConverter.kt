package au.gov.dxa.monitor.emit

import au.gov.dxa.monitor.ingestion.Observation

class MetricConverter {


    fun convert(fetched:List<Observation>, quantity:Int):Map<String, List<Any?>>{
        val metrics = mutableMapOf<String,MutableList<Any?>>()

        val metricNames = getAllMetricNames(fetched)


        for(observation in fetched){
            for(metricName in metricNames){
                var theValue:Any? = null
                if(observation.calculatedVariables.containsKey(metricName)){
                    theValue = observation.calculatedVariables[metricName]
                }
                if(!metrics.containsKey(metricName)) metrics[metricName] = mutableListOf()
                metrics[metricName]!!.add(theValue)
            }
        }

        for(metric in metrics){
            val gap = quantity - metric.value.size
            if(gap == 0) continue
            for(i in 1..gap) metric.value.add(0,null)

        }


        return metrics
    }

    private fun getAllMetricNames(fetched: List<Observation>):List<String> {
        val metricNames = mutableListOf<String>()
        for (observation in fetched) {
            for (value in observation.calculatedVariables) {
                if (!metricNames.contains(value.key)) metricNames.add(value.key)
            }
        }
        return metricNames
    }


}