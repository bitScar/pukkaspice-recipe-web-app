<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
 
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
    
    <div class='carousel slide over-something' id='homepage-carousel'>
        <div class='carousel-inner slider-w'>
          <div class='active item'>
            <div class='container'>
              <h1 class='slider-header'>welcome <strong>spice</strong> lovers.</h1>
              <h2 class='slider-sub-header'>What can you do with a few spices?</h2>
              <div class='cta'>
                <a href="/secure/join" class="btn btn-cta">Start Spicing Here</a>
              </div>
              <div class='slider-browsers-w clearfix'>
                <div class='slider-browser slider-browser-left hidden-phone' data-position-bottom='-8%'>
                  <img alt="Browser-window-2" src="resources/assets/images/browser-window-ginger.png" />
                </div>
                <div class='slider-browser slider-browser-center' data-position-bottom='-9%'>
                  <img alt="Browser-window-1" src="resources/assets/images/browser-window-chilli.png" />
                </div>
                <div class='slider-browser slider-browser-right hidden-phone' data-position-bottom='-8%'>
                  <img alt="Browser-window-3" src="resources/assets/images/browser-window-nutmeg.png" />
                </div>
              </div>
            </div>
          </div>
      </div>
      
      <section class='section-wrapper under-slider'>
        <div class='container'>
          <div class='row'>
            <div class='span12'>
              <h3 class='section-header'>Yummy Recipes</h3>
            </div>
            <div class='span3'>
              <div class='white-card'>
                <div class="img-w hover-fader">
                  <a href="resources/assets/images/recipes/dopiaza.png" data-rel="lightbox[gallery]"><img alt="Photo-card" src="resources/assets/images/recipes/dopiaza.png">
                    <span class="hover-fade">
                      <i class="icon-zoom-in"></i>
                    </span>
                  </a>
                </div>
                <h5>Lamb Dopiaza</h5>
                <p>Easy and tasty way to make a curry that you will ever find. A true one-pot wonder from Rick Stiens travals to India.</p>
              </div>
            </div>
            <div class='span3'>
              <div class='white-card'>
                <div class="img-w hover-fader">
                  <a href="resources/assets/images/recipes/spicy-chicken-wings.png" data-rel="lightbox[gallery]"><img alt="Spicy Chicken Wings" src="resources/assets/images/recipes/spicy-chicken-wings.png">
                    <span class="hover-fade">
                      <i class="icon-zoom-in"></i>
                    </span>
                  </a>
                </div>
                <h5>Spicy Chicken Wings.</h5>
                <p>A chinese takeaway favorite that is so tasty and easy to make you'll think twice about picking up the phone next time.</p>
              </div>
            </div>
            <div class='span3'>
              <div class='white-card'>
                <div class="img-w hover-fader">
                  <a href="resources/assets/images/recipes/nann-bread.png" data-rel="lightbox[gallery]"><img alt="Photo-card" src="resources/assets/images/recipes/nann-bread.png">
                    <span class="hover-fade">
                      <i class="icon-zoom-in"></i>
                    </span>
                  </a>
                </div>
                <h5>Naan Bread.</h5>
                <p>You might not have a tandoor, but you can sill make a version of this bread at home without a tandoor.</p>
              </div>
            </div>
            <div class='span3'>
              <div class='white-card'>
                <div class="img-w hover-fader">
                  <a href="resources/assets/images/recipes/thai-curry.png" data-rel="lightbox[gallery]"><img alt="Photo-card" src="resources/assets/images/recipes/thai-curry.png">
                    <span class="hover-fade">
                      <i class="icon-zoom-in"></i>
                    </span>
                  </a>
                </div>
                <h5>Thai Yellow Curry.</h5>
                <p>Fragrant with lemongrass, basil and galangal, this is sure be a favorate once you know how easy it is to cook.</p>
              </div>
            </div>
          </div>
        </div>
      </section>
 
    </tiles:putAttribute>
</tiles:insertDefinition>
