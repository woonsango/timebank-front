//server.js
//테스트 서버
const express = require("express");
const app = express();
const port = process.env.PORT || 5000;

//Cross Origin Resource Sharing
const cors = require("cors");

const bodyParser = require("body-parser");

app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors());
app.use(bodyParser.json());

app.post("/authorization", (req, res) => {
  const authorizationCode = req.body;
  console.log("front로부터 받은 인가 코드:", authorizationCode);

  res.json({ message: "save authorization!", authorizationCode });
});

app.listen(port, () => console.log(`Listening on port ${port}`));
