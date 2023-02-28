import React from 'react'
import About from './components/about/About.jsx'
import Contact from './components/contact/Contact.jsx'
import Footer from './components/footer/footer'
import Header from './components/header/header'
import Nav from './components/nav/nav'
import Portfolio from './components/portfolio/portfolio'
import A from './components/experience/a'
import Services from './components/services/services'
import Testimonials from './components/testimonials/testm'



const App = () => {
  return (
    <>
        <Header/>
        <Nav/>
        <About/>
        <A/>
        <Services/>
        <Portfolio/>
        <Testimonials/>
        <Contact/>
        <Footer/>
       
        
        
        

    
    </>

  )
}

export default App