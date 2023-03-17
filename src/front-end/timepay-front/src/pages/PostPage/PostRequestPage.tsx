import { Layout, Input, DatePicker, Space } from 'antd';
import { useNavigate } from 'react-router-dom';
import { useCallback, useRef, useState, useEffect } from 'react';
import { ReactComponent as BackArrow } from '../../assets/images/icons/header-back-arrow.svg';
import { cssMainHeaderStyle } from '../../components/MainHeader/MainHeader.styles';
import {
  cssPostPageStyle,
  cssPostTitleInputStyle,
  cssLineStyle,
  cssPostContentInputStyle,
  cssPostBtnStyle,
  cssPostFooterStyle,
} from './PostFreePage.style';

import { CameraFilled, FlagFilled } from '@ant-design/icons';
import { Modal, Upload, TimePicker, Tag } from 'antd';
import type { RcFile, UploadProps } from 'antd/es/upload';
import type { UploadFile } from 'antd/es/upload/interface';
import dayjs from 'dayjs';

const getBase64 = (file: RcFile): Promise<string> =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result as string);
    reader.onerror = (error) => reject(error);
  });

const { Header, Content, Footer } = Layout;
const { TextArea } = Input;
const { RangePicker } = DatePicker;
const { CheckableTag } = Tag;

const format = 'HH:mm';
const tagsData = [
  '생활',
  '건강',
  '산책',
  '봉사',
  '교육',
  '학교',
  '몰라',
  '도움이필요해요',
];

const PostFreePage = () => {
  const timepay = 500;
  const [starttime, setStarttime] = useState();
  const [endtime, setEndtime] = useState();

  const navigate = useNavigate();

  // textarea 관련
  const textareaRef = useRef<HTMLTextAreaElement | null>(null);

  const [value, setValue] = useState<String>();

  useEffect(() => {
    if (textareaRef && textareaRef.current) {
      textareaRef.current.style.height = '0px';
      const scrollHeight = textareaRef.current.scrollHeight;
      textareaRef.current.style.height = scrollHeight + 'px';
    }
  }, [value]);

  /*
  const scrollRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    scrollRef.current?.scrollTo(0, scrollRef.current.scrollHeight);
  });
  */

  // 뒤로가기
  const handleClickBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  // 이미지 업로드
  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewImage, setPreviewImage] = useState('');
  const [previewTitle, setPreviewTitle] = useState('');
  const [fileList, setFileList] = useState<UploadFile[]>([]);
  const handleCancel = () => setPreviewOpen(false);

  const handlePreview = async (file: UploadFile) => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj as RcFile);
    }

    setPreviewImage(file.url || (file.preview as string));
    setPreviewOpen(true);
    setPreviewTitle(
      file.name || file.url!.substring(file.url!.lastIndexOf('/') + 1),
    );
  };

  const handleChange: UploadProps['onChange'] = ({ fileList: newFileList }) =>
    setFileList(newFileList);

  const uploadButton = (
    <div>
      <CameraFilled />
      <div style={{ marginTop: 8 }}>이미지 업로드</div>
    </div>
  );

  // 태크
  const [selectedTags, setSelectedTags] = useState<string[]>(['Books']);

  const handleTagChange = (tag: string, checked: boolean) => {
    const nextSelectedTags = checked
      ? [...selectedTags, tag]
      : selectedTags.filter((t) => t !== tag);
    console.log('You are interested in: ', nextSelectedTags);
    setSelectedTags(nextSelectedTags);
  };

  return (
    <Layout css={cssPostPageStyle}>
      <div className="wrapper">
        <Header css={cssMainHeaderStyle}>
          <BackArrow onClick={handleClickBack} />
          <span>도움받기</span>
        </Header>
        <Content style={{ paddingTop: 60 }}>
          <input
            css={cssPostTitleInputStyle}
            placeholder="제목을 입력하세요"
            maxLength={25}
          />
          <div css={cssLineStyle} />
          <h6>관련 카테고리 설정</h6>
          <Space size={[0, 8]} wrap>
            {tagsData.map((tag) => (
              <CheckableTag
                key={tag}
                checked={selectedTags.includes(tag)}
                onChange={(checked) => handleTagChange(tag, checked)}
                style={{
                  marginTop: '5px',
                  marginLeft: '20px',
                  paddingTop: '5px',
                  paddingBottom: '5px',
                  paddingLeft: '10px',
                  paddingRight: '10px',
                  borderRadius: '10px',
                  fontSize: '16px',
                  backgroundColor: '#E8E8E8',
                }}
              >
                {tag}
              </CheckableTag>
            ))}
          </Space>
          <div css={cssLineStyle} />
          <h6>날짜</h6>
          <RangePicker
            size={'large'}
            style={{ marginLeft: '20px' }}
            placeholder={['시작일', '종료일']}
          />
          <h6>시간</h6>
          <TimePicker
            value={starttime}
            format={format}
            size={'large'}
            style={{ marginLeft: '20px' }}
            placeholder={'시작 00:00'}
          />
          <TimePicker
            value={endtime}
            format={format}
            size={'large'}
            style={{ marginLeft: '20px' }}
            placeholder={'종료 23:59'}
          />
          <p>보유하고 있는 타임페이 : {timepay}</p>
          <p>소모 타임페이 : {timepay}</p>
          <div css={cssLineStyle} />
          <h6>장소</h6>
          <Input
            size="large"
            placeholder="도움받기를 희망하는 장소를 입력하세요 :)"
            style={{ marginLeft: '20px', paddingLeft: '15px', width: '400px' }}
            prefix={<FlagFilled style={{ marginRight: '5px' }} />}
          />
          <div css={cssLineStyle} />
          <TextArea
            rows={10}
            bordered={false}
            style={{ resize: 'none' }}
            css={cssPostContentInputStyle}
            placeholder="내용을 입력하세요"
          />
          <div css={cssLineStyle} />
          <h5>사진 ({fileList.length}/5)</h5>
          <Upload
            className="upload"
            action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
            listType="picture-card"
            fileList={fileList}
            onPreview={handlePreview}
            onChange={handleChange}
          >
            {fileList.length >= 5 ? null : uploadButton}
          </Upload>
          <Modal
            open={previewOpen}
            title={previewTitle}
            footer={null}
            onCancel={handleCancel}
          >
            <img alt="example" style={{ width: '100%' }} src={previewImage} />
          </Modal>
        </Content>
      </div>
      <Footer css={cssPostFooterStyle}>
        <button css={cssPostBtnStyle}>작성완료</button>
      </Footer>
    </Layout>
  );
};

export default PostFreePage;
