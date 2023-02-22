const express = require("express");
const cors = require("cors");
const dotenv = require("dotenv").config();
const bodyParser = require("body-parser");
const cookieParser = require("cookie-parser");
const helmet = require("helmet");
const routes = require("./routes/routes");
const morgan = require("morgan");

//setting up your port
const PORT = process.env.PORT;
const corsOptions = { credentials: true, origin: process.env.URL || "*" };

const app = express();

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

//Proxy Routes
app.use(routes);


app.use(cors(corsOptions));
app.use(helmet());
app.use(cookieParser());

app.use(morgan("dev"));



app.listen(PORT, () => console.log(`Gateway online on http://localhost:${PORT}`));
