FROM groovy:3.0.4-jre
ADD scripts /scripts

CMD groovy /scripts/RequestChecker.groovy