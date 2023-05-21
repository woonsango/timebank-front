import { Button, Form, Input, InputNumber, message, Modal, Select } from 'antd';
import { useCallback, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { useGetCategory } from '../../api/hooks/category';
import { usePostDonationBoardWrite } from '../../api/hooks/donation';
import { headerTitleState } from '../../states/uiState';
import { PATH } from '../../utils/paths';
import { cssDonationBoardWritePage } from './DonationBoardWritePage.styles';

const DonationBoardWritePage = () => {
  const navigate = useNavigate();
  const { data } = useGetCategory({
    type: '도움요청',
    useYn: 'Y',
  });
  const usePostDonationBoardWriteMutation = usePostDonationBoardWrite();

  const setHeaderTitle = useSetRecoilState(headerTitleState);

  useEffect(() => {
    setHeaderTitle('기부하기 게시글 작성');
  });
  const [messageApi, contextHolder] = message.useMessage();

  const handleOnSubmitDonationBoard = useCallback(
    (values: any) => {
      console.log(values);
      Modal.confirm({
        content: '해당 내용으로 게시글을 등록하시겠습니까?',
        okText: '확인',
        cancelText: '취소',
        onOk: async () => {
          await usePostDonationBoardWriteMutation.mutateAsync(
            {
              ...values,
              targetTimePay: parseInt(values.targetTimePay),
            },
            {
              onSuccess: (data) => {
                messageApi.success({
                  content: '등록이 완료되었습니다.',
                  duration: 1,
                  onClose: () => {
                    if (data && data.data && data.data.id)
                      navigate(`${PATH.DONATION_BOARD}/${data.data.id}`);
                  },
                });
              },
            },
          );
        },
      });
    },
    [usePostDonationBoardWriteMutation, messageApi, navigate],
  );

  return (
    <div css={cssDonationBoardWritePage}>
      {contextHolder}
      <Form onFinish={handleOnSubmitDonationBoard}>
        <Form.Item
          label="목표 타임페이"
          name="targetTimePay"
          rules={[{ required: true, message: '필수로 입력해주세요.' }]}
          extra="개인 회원들에게 노출됩니다."
        >
          <InputNumber addonAfter="TP" />
        </Form.Item>
        <Form.Item
          label="카테고리"
          name="category"
          rules={[{ required: true, message: '필수로 입력해주세요.' }]}
          initialValue={
            data && data.data && data.data.length > 0
              ? data?.data[0]
              : undefined
          }
          extra={<>기관이나 기부 목적에 맞게 카테고리를 지정해주세요.</>}
        >
          <Select>
            {data?.data.map((category) => (
              <Select.Option
                key={category.categoryId}
                value={category.categoryName}
              >
                {category.categoryName}
              </Select.Option>
            ))}
          </Select>
        </Form.Item>
        <Form.Item
          label="제목"
          name="title"
          rules={[{ required: true, message: '필수로 입력해주세요.' }]}
        >
          <Input max={20} />
        </Form.Item>{' '}
        <Form.Item
          label="내용"
          name="content"
          rules={[{ required: true, message: '필수로 입력해주세요.' }]}
          extra="제목/내용에는 기부 받을 타임페이의 사용처와 기관에 대한 설명을 넣어주세요. "
        >
          <Input.TextArea rows={10} />
        </Form.Item>
        <Button type="primary" htmlType="submit">
          등록
        </Button>
      </Form>
    </div>
  );
};

export default DonationBoardWritePage;
