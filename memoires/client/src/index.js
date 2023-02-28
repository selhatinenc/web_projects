import React from 'react';
import  ReactDOM  from 'react-dom';
import App from './app';

ReactDOM.render(<App/>, document.getElementById("root"));

const app= express();

app.use(bodyParser.json({}))