angular.module('Charts', [])
  .service('D3', function D3() {
		  return window.d3;
	})
  .directive('circularProgress', function(D3) {
    return {
      template: '<div class="circularProgress"><svg></svg></div>',
      replace: true,
      scope: {
        'ngModel': '='
      },
      link: function (scope, element, attrs) {

        

        var el = D3.select('.circularProgress');
        var w = attrs.width || el.node().clientWidth;
        var h = attrs.height || el.node().clientHeight;
        var r = Math.min(w, h) / 2;
        var pi = Math.PI;

    var svgContainer = el.select('svg')
          .attr('width', w)
          .attr('height', h)
          .append('g')
          .attr('transform', 'translate(' + w/2 + ',' + h/2 + ')');

        var display = svgContainer.append('text')
                .style("text-anchor", "middle")
                .attr('y', 5)
		        .text((Math.round(scope.ngModel * 100)) + "%");
        
        var arcBackground = D3.svg.arc()
        .innerRadius(r * 0.7)
        .outerRadius(r * 0.9)
        .startAngle(2 * Math.PI) //converting from degs to radians
        .endAngle(0); //just radians
        
        svgContainer.append("path")
        .style("fill", "ffffff")
        .attr("d", arcBackground);
        
        var arc = D3.svg.arc()
          .innerRadius(r * 0.7)
          .outerRadius(r * 0.9)
          .startAngle(2 * Math.PI * scope.ngModel) //converting from degs to radians
          .endAngle(0); //just radians
          
        svgContainer.append("path")
        	.style("fill", "93cfeb")
		    .attr("d", arc);

      }
    };	  
  });