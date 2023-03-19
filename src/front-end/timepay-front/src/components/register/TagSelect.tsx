import { useState } from 'react';
import { useRecoilState } from 'recoil';
import { Tag, Space } from 'antd';
import { selectedTagsState } from '../../states/register';

const { CheckableTag } = Tag;

const tagsData = ['생활', '건강', '산책', '봉사', '교육', '친목', '도와주세요'];

export default function TagSelect() {
  // Recoil state
  const [selectedTags, setSelectedTags] = useRecoilState(selectedTagsState);

  const handleTagChange = (tag: string, checked: boolean) => {
    const nextSelectedTags = checked
      ? [...selectedTags, tag]
      : selectedTags.filter((t) => t !== tag);
    setSelectedTags(nextSelectedTags);
  };

  return (
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
            borderRadius: '20px',
            fontSize: '16px',
            fontWeight: '600',
            border: '1px solid #ffc700',
          }}
        >
          {tag}
        </CheckableTag>
      ))}
    </Space>
  );
}
