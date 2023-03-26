//server.js
//테스트 서버
const express = require("express");
const app = express();
const port = process.env.PORT || 8080;

//Cross Origin Resource Sharing
const cors = require("cors");

const bodyParser = require("body-parser");

app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors());
app.use(bodyParser.json());

const multer = require("multer");
const form_data = multer();
app.use(form_data.array());

/*프론트에서 인가코드 받아오기 */
app.post("/code", (req, res) => {
  const authorizationCode = req.body;

  res.json({
    message: "서버가 인가코드를 성공적으로 받았습니다.",
    authorizationCode,
  });
});

/*프론트에게 uid 보내주기*/
app.post("/uid", (req, res) => {
  const uid = 1234567;

  res.json({ message: "서버가 uid를 전송했습니다. ", uid });
});

/*프론트에게 유저 정보와 uid 받아오고 보내주기 */
app.post("/info", (req, res) => {
  const data = req.body;
  const uid = 1234567;
  console.log("프론트로부터 유저 정보를 받았습니다. ", data);

  res.json({ message: "서버가 유저 정보를 성공적으로 받았습니다." });
});

app.listen(port, () => console.log(`Listening on port ${port}`));
