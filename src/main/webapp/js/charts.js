angular.module('Charts', []).service('D3', function D3() {
	return window.d3;
})
.directive('linechart',
		function(D3) {
			return {
				restrict: 'EA',
				scope : {
					'ngModel' : '=',
					'xAxisFormat' : '&',
					'yAxisFormatFunction' : '&'
				},
				link : function(scope, element, attrs) {
			          var svg = d3.select(element[0])
			              .append("svg")
			              .attr("class", "linechart")
			              .attr("width", "100%");

			          var timeFormat = d3.time.format("%d-%m");
			          var yAxisFormatFunction = function(d) {
			        	  return d;
			          };
					  if (angular.isDefined(scope.yAxisFormatFunction)) {
						  yAxisFormatFunction = scope.yAxisFormatFunction();
					  }

			          var timeFormat = d3.time.format("%d-%m");
			          var invertYAxis = attrs.invertYAxis;
			          
			          var margin = {top: 20, right: 20, bottom: 30, left: 50};
			          
			          window.onresize = function() {
			              return scope.$apply();
			          };
			          scope.$watch(function(){
			                return angular.element(window)[0].innerWidth;
			              }, function(){
			                return scope.render(scope.ngModel);
			              }
			          );
			          
			          // watch for data changes and re-render
			          scope.$watch('ngModel', function(newValue, oldValue) {
			            return scope.render(newValue, oldValue);
			          }, false);
			          
			          
			          
			          scope.render = function(data, oldValue) {
			        	    svg.selectAll('*').remove();

			        	    // If we don't pass any data, return out of the element
			        	    if (!data) return;
			        	    
					        var width = d3.select(element[0]).node().offsetWidth - margin.left - margin.right;
					        var height = d3.select(element[0]).node().offsetHeight - margin.top - margin.bottom;
					        
			        	    var chart = svg.append("g")
			        	    	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
			        	    
			        	    var x = d3.time.scale()
			        	    .range([0, width]);
			        	    
			        	    var from = height;
			        	    var to = 0;
			        	    
			        	    if (invertYAxis) {
			        	    	from = 0;
			        	    	to = height;
			        	    }
			        	    
				        	var y = d3.scale.linear()
				        	    .range([from, to]);
	
				        	var xAxis = d3.svg.axis()
				        		.ticks(5)
				        	    .scale(x)
				        	    .tickFormat(timeFormat)
				        	    .orient("bottom");
	
				        	var yAxis = d3.svg.axis()
				        		.ticks(5)
				        	    .scale(y)
				        	    .tickFormat(yAxisFormatFunction)
				        	    .orient("left");
	
				        	var line = d3.svg.line()
				        	    .x(function(d) { 
				        	    	return x(d.key); 
				        	    })
				        	    .y(function(d) { 
				        	    	return y(d.value); 
				        	    });
				        	
				        	x.domain(d3.extent(data, function(d) { 
				        		return d.key; 
				        	}));
				        	y.domain(d3.extent(data, function(d) { 
				        		return d.value; 
				        	}));
				        
				        	
				        	chart.append("g")
				              .attr("class", "x axis")
				              .attr("transform", "translate(0," + height + ")")
				              .call(xAxis);

				            chart.append("g")
				              .attr("class", "y axis")
				              .call(yAxis);
				        	
				            chart.append("path")
				              .datum(data)
				              .attr("class", "line")
				              .attr("d", line);
				            
				            var currentText = "";
				            
				            var tooltip = chart.append("text")
							.attr("class", "charttip");
				            
				            chart.selectAll("dot")
				            .data(data)
				            .enter().append("circle")
				            .attr("r", 3.5)
				            .attr("cx", function(d) { return x(d.key); })
				            .attr("cy", function(d) { return y(d.value); })
				            .attr("class", "circle")
				            .on("mouseover", function(d) {
				            	tooltip
				            		.attr("x", function(value) {
				            			return x(d.key);
				            		})
				            		.attr("y", function(value) {
				            			return y(d.value) - 5; 
				            		})
				            		.text(d3.time.format("%d-%m-%y")(new Date(d.key)) + ": " + d.label);
				            });
				            


			          };
				}
			}
})
.directive('histogram',
		function(D3) {
	return {
		restrict: 'EA',
		scope : {
			'ngModel' : '=',
			'xAxisFormat' : '&'
		},
		link : function(scope, element, attrs) {
			var svg = d3.select(element[0])
			.append("svg")
			.attr("class", "histogram")
			.attr("width", "100%");
			
			var xMargin = parseInt(attrs.xMargin) || 20,
			yMargin = parseInt(attrs.yMargin) || 20,
			barWidth = parseInt(attrs.barWidth) || 15,
			barPadding = parseInt(attrs.barPadding) || 1;
			
			window.onresize = function() {
				return scope.$apply();
			};
			scope.$watch(function(){
				return angular.element(window)[0].innerWidth;
			}, function(){
				return scope.render(scope.ngModel);
			}
			);
			
			// watch for data changes and re-render
			scope.$watch('ngModel', function(newValue, oldValue) {
				return scope.render(newValue, oldValue);
			}, false);
			
			
			
			scope.render = function(data, oldValue) {
				svg.selectAll('*').remove();
				
				// If we don't pass any data, return out of the element
				if (!data) return;
				
				var width = d3.select(element[0]).node().offsetWidth - xMargin;
				barWidth = (width) / (data.length | 1);
				var height = d3.select(element[0]).node().offsetHeight - yMargin;
				
				//var width = data.length * (barWidth + barPadding);
				
				var yScale = d3.scale.linear()
				.domain([0, d3.max(data, function(d) {
					return d.value;
				})])
				.range([0, height]);
				
				// set the weight based on the calculations above
				svg.attr('width', width);
				
				var x = d3.scale.linear()
				.domain([0, data.length])
				.range([0, width]);
				
				var y = d3.scale.linear()
				.domain([0, 1])
				.range([height, 0]);
				
				
				//create the rectangles for the bar chart
				var bar = svg.selectAll('.bar')
				.data(data).enter()
				.append('g')
				.attr('class', 'bar')
				.attr("transform", function(d) { 
					return "translate(" + x(d.key) + "," + y(d.value) + ")"; 
				});
				
				bar.append("rect")
				.attr("x", 1)
				.attr("width", x(1) - 1) // empty space between bars
				.attr("fill", "#428bca")
				.attr("height", function(d) { 
					return height - y(d.value); 
				});			
				
				bar.append("text")
				.attr("y", -3)
				.attr("class", "text-small")
				.attr("x", barWidth/6)
				.text(function(d) { 
					return d.value > 0 ? 
							d3.format(".2p")(d.value) : null }); 
				
				
				var xAxis = d3.svg.axis()
				.scale(x)
				.tickSubdivide(true)
				.tickFormat(function(d) { 
					var currentElement = data[d];
					if (angular.isDefined(currentElement)) {
						return currentElement.label; 
					} else {
						return "";
					}
				})
				.orient("bottom");  
				
				
				svg.append("g")
				.attr("class", "x axis")
				.attr("transform", "translate("+ (x(1) /2) + "," + height + ")")
				.call(xAxis);
			};
		}
	}
})
.directive('circularProgress',
		function(D3) {
			return {
				restrict: 'EA',
				scope : {
					'ngModel' : '='
				},
				link : function(scope, element, attrs) {
			          var svg = d3.select(element[0])
			              .append("svg")
			              .attr("class", "circularProgress")
			              .attr("width", "100%");
			          
			          window.onresize = function() {
			              return scope.$apply();
			          };
			          scope.$watch(function(){
			                return angular.element(window)[0].innerWidth;
			              }, function(){
			                return scope.render(scope.ngModel);
			              }
			          );
			          
			          // watch for data changes and re-render
			          scope.$watch('ngModel', function(newValue, oldValue) {
			            return scope.render(newValue, oldValue);
			          }, false);
			          
			          
			          
			          scope.render = function(newValue, oldValue) {
			              // remove all previous items before render
			              svg.selectAll("*").remove();

			              var w = attrs.width || element[0].node().clientWidth;
			              var h = attrs.height || element[0].node().clientHeight;
			              var r = Math.min(w, h) / 2;
			              var pi = Math.PI;
		
			              var svgContainer = svg
			              	.attr('width', w)
			              	.attr('height', h)
			              	.append('g')
			              	.attr('transform', 'translate(' + w / 2 + ',' + h / 2 + ')');
		
			              var display = svgContainer
			              	.append('text')
			              	.style("text-anchor", "middle")
			              	.attr("fill", "#aaaaaa")
			              	.attr("dominant-baseline", "central")
			              	.text((Math.round(scope.ngModel * 100)) + "%");
		
			              var arc = D3.svg.arc()
								.innerRadius(r * 0.7)
								.outerRadius(r * 0.9)
								.startAngle(0); //converting from degs to radians
		
		
			              var background = svgContainer
								.append("path")
								.style("fill", "ffffff")
								.datum({endAngle: 2 * Math.PI})
								.attr("d", arc);

			              var foreground = svgContainer
								.append("path")
								.style("fill", "93cfeb")
								.datum({endAngle: 2 * Math.PI * oldValue || 0})
								.attr("d", arc);
			              
						  foreground.transition().duration(750).call(arcTween,
									2 * (newValue || 0) * Math.PI);

			              function arcTween(transition, newAngle) {
			            	  
			            	  transition.attrTween("d", function(d) {
			            		  
			            		  var interpolate = d3.interpolate(d.endAngle, newAngle);
			            		  
			            		  return function(t) {
			            			  d.endAngle = interpolate(t);
			            			  return arc(d);
			            		  };
			            	  });
			              };
			            };
			            

				}
			};
		});