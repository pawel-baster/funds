# cor = cov2cor(var(mbank.extract[, 2:ncol(mbank.extract)], na.rm=TRUE, use="pairwise.complete.obs"))
mbank.extract <- read.csv("../data/mbank-extract.csv")
funds = c()
start = 1000
for (i in 2:254) { 
  if (!is.na(min(mbank.extract[start:nrow(mbank.extract), i]))) {
    funds = c(funds, i)
    value = floor(mbank.extract[nrow(mbank.extract), i]/mbank.extract[start, i]*100)
    cat(i, value, "\n")
    print(paste(colnames(mbank.extract)[i], format(value), sep=""))
    colnames(mbank.extract)[i] <- paste(colnames(mbank.extract)[i], format(value), sep="_")
  }
}

data = mbank.extract[(start+1):nrow(mbank.extract), funds] / mbank.extract[start:(nrow(mbank.extract)-1), funds]
dist <- dist(t(data))
plot(hclust(dist, method="ward"))

