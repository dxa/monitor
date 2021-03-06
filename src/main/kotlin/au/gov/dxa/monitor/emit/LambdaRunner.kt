package au.gov.dxa.monitor.emit

import au.gov.dxa.monitor.Config
import au.gov.dxa.monitor.ingestion.Observation
import javax.script.ScriptEngineManager

data class LambdaResults(val results:List<LambdaResult>)
data class LambdaResult(val name:String, val result:Any)
class LambdaRunner(var observation:Observation) {
    val engine = ScriptEngineManager().getEngineByName("nashorn")
    var globalVars = ""

    init{
        for(variable in observation.monitoringValues){
            val value = if(variable.value is String) "\"${variable.value}\"" else "${variable.value}"
            val declaration = "var ${variable.key} = $value;\n"
            globalVars = globalVars + declaration
        }
    }

    fun eval(lambda:String):Any{
        return engine.eval(globalVars + "\n" + lambda + ";")
    }

    fun evalLambdas(config: Config):MutableMap<String, Any>{
        val map = mutableMapOf<String, Any>()
        for(lambda in config.lambdas) {
            map[lambda.name] =  eval(lambda.lambda)
        }
        return map
    }

}