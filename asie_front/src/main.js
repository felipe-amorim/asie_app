import React from "react";
import Home from "./components/home/home";
import { Container } from 'react-materialize';
import { Switch, Route } from 'react-router-dom'
import Planning from "./components/suite/planning/planning";
import ProductDetails from "./components/suite/planning/details/details";
import CreateNewProduct from "./components/suite/planning/create/create_new_product";


function Main() {
  return ( <main>
    <Container>
      <Switch>
        <Route exact path='/' component={Home} />
        <Route path='/product-details/:id' component={ProductDetails} />
        <Route path='/planning' component={Planning} />
        <Route path='/create-new-product' component={CreateNewProduct} />
      </Switch>
    </Container>
  </main>
  );
};

export default Main;
