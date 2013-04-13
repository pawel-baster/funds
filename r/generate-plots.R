#!/usr/bin/Rscript

models = c("Mbank-basic", "Mbank-ward", "Mbank-bestRanked", "Mbank-full", "MBank-alianz", "MBank-amplicoB", "MBank-amplicoG", "MBank-axa", "MBank-bph", "MBank-hsbc", "MBank-ing1", "MBank-ing2", "MBank-investor", "MBank-legg", "MBank-noble", "MBank-pko1", "MBank-pko2", "MBank-pzu1", "MBank-pzu2", "MBank-skarbiec", "MBank-uni", "MBank-woif")
#colors = rainbow(4)

plot_best_history <- function() {
  
  datasets = list()
  
  ymax = 0
  
  for (i in 1:length(models)) {
    datasets[[i]] = read.table(sprintf("data/%s_best_history.csv", models[i])    , sep=";", quote="\"")
    ymax = max(ymax, datasets[[i]][,2])
  }
  
  period = 1:nrow(datasets[[1]])
  
  colors = rainbow(length(models))
  
  plot(function (x) { 1.02 ^ (x/365) }, type="l", col="black", ylim=c(0, ymax), xlim=c(1,length(datasets[[i]][,2])), ylab="", xlab="", xaxt = "n")
  legend("topleft", models, col=colors, lty=1)
  
  for (i in 1:length(models)) {
    lines(datasets[[i]][period,2], type="l", col=colors[i])
  }  
}

plot_experiment_history <- function(includeBestHistory) {
    
  datasets = list()
  
  ymax = 0
  ymin = 2
  
  for (i in 1:length(models)) {
    datasets[[i]] = read.table(sprintf("data/%s_experiment_history.csv", models[i])    , sep=";", quote="\"")
    ymax = max(ymax, datasets[[i]][,2])
    ymin = min(ymin, datasets[[i]][,2])
    if (includeBestHistory) {
      datasets[[i]][, 4] = datasets[[i]][, 4]/datasets[[i]][1, 4]
      
      ymin = min(ymin, min(datasets[[i]][,4]))
      ymax = max(ymax, max(datasets[[i]][,4]))
    } 
  }

  colors = rainbow(length(models))
  
  plot(function (x) { 1.02 ^ (x/365) }, type="l", col="black", ylim=c(ymin, ymax), xlim=c(1,length(datasets[[i]][,2])), ylab="", xlab="", xaxt = "n")
  axis(1, at=1:length(datasets[[i]][,1]), labels=datasets[[i]][,1], las=2)
  legend("bottomleft", models, col=colors, lty=1)
    
  for (i in 1:length(datasets)) {
    lines(datasets[[i]][,2], col=colors[i], ylim=c(ymin, ymax), lwd=2)
    if (includeBestHistory)
      lines(datasets[[i]][,4], col=colors[i], ylim=c(ymin, ymax), lty=2)
  }
}

width = 12
height = 7

svg(file="data/best_history.svg", width=width, height=height)
plot_best_history()
dev.off()

svg(file="data/experiment_history.svg", width=width, height=height)
plot_experiment_history(F)
dev.off()

svg(file="data/experiment_history_with_best.svg", width=width, height=height)
plot_experiment_history(T)
dev.off()