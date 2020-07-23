import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def path = System.getenv("INPUT_FILE")
assert path: "Set INPUT_FILE environment!"

def json = new JsonSlurper().parse(new File(path))

for (def request : json) {
    doRequest(request)
}

void doRequest(request) {
    def baseUrl = new URL(request.requestUrl)
    def connection = baseUrl.openConnection()
    connection.with {
        doOutput = true
        requestMethod = request.requestMethod
        if (request.requestBody) {
            outputStream.withWriter { writer ->
                writer << request.requestBody
            }
        }
        println "👁    ${request.requestUrl}: ${content.text}"
        def expectedResponse = request.expectedResponse
        if (expectedResponse) {
            assert content.text == JsonOutput.toJson(request.expectedResponse)
        }
    }

}