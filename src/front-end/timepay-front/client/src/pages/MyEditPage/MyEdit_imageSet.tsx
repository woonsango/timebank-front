import { useState, useRef } from 'react';
import { Avatar } from 'antd';
import './MyEdit.css';

const MyEdit_imageSet = () => {
  const [image, setImage]: any = useState(
    'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
  );
  const fileInput = useRef<any>();

  <Avatar
    src={image}
    style={{ margin: '20px' }}
    size={200}
    onClick={() => {
      fileInput.current.click();
    }}
  />;

  const handleFileChange = (e: any) => {
    const imageFile = e.target.files[0];
    const last = imageFile;
    if (imageFile !== null) {
      setImage(imageFile);
    } else {
      setImage(
        'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
      );
      return;
    }

    //프로필 사진 시각화
    const fileReader = new FileReader();
    fileReader.onload = () => {
      if (fileReader.readyState == 2) {
        if (fileReader.result !== null) {
          setImage(fileReader.result);
        }
      }
    };
    fileReader.readAsDataURL(imageFile); //setImage
  };

  return (
    <div>
      <img src={image} className="EditProfileImage" />
      <div className="EditProfileImageWrap">
        <label htmlFor="image_guide">
          프로필 사진 수정하기
          <input
            type="file"
            id="image_guide"
            src={image}
            accept="image/*"
            onChange={handleFileChange}
          />
        </label>
      </div>
    </div>
  );
};

export default MyEdit_imageSet;
