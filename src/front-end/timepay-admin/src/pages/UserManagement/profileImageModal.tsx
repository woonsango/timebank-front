import { Modal, Image, Button } from 'antd';
import { useState } from 'react';

const ProfileImageModal = () => {
  /*프로필 사진 모달 설정 */
  const [modalProfileImage, setModalProfileImage] = useState(false);

  const showModalProfileImage = () => {
    setModalProfileImage(true);
  };
  const handleOkProfileImage = () => {
    setModalProfileImage(false);
  };

  const handleCancelProfileImage = () => {
    setModalProfileImage(false);
  };

  return (
    <div>
      <Button onClick={showModalProfileImage}>사진 보기</Button>
      <Modal
        title="프로필 사진"
        open={modalProfileImage}
        onOk={handleOkProfileImage}
        onCancel={handleCancelProfileImage}
        okText="확인"
        cancelText="취소"
      >
        <Image
          width={200}
          src="https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png"
        />
      </Modal>
    </div>
  );
};

export default ProfileImageModal;
