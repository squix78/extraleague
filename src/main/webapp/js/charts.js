angular.module('Charts', []).service('D3', function D3() {
	return window.d3;
}).directive(
		'circularProgress',
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
		
			              var svgContainer = svg.attr('width', w).attr(
									'height', h).append('g').attr('transform',
									'translate(' + w / 2 + ',' + h / 2 + ')');
		
			              var display = svgContainer.append('text').style(
									"text-anchor", "middle").attr('y', 4).text(
									(Math.round(scope.ngModel * 100)) + "%");
		
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