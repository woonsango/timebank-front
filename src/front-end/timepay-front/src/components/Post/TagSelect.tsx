import { useState } from 'react';
import { Tag, Space } from 'antd';

const { CheckableTag } = Tag;

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

export default function TagSelect() {
  // 태크
  const [selectedTags, setSelectedTags] = useState<string[]>(['']);

  const handleTagChange = (tag: string, checked: boolean) => {
    const nextSelectedTags = checked
      ? [...selectedTags, tag]
      : selectedTags.filter((t) => t !== tag);
    console.log('You are interested in: ', nextSelectedTags);
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
            borderRadius: '10px',
            fontSize: '16px',
            border: '1px solid #ffc700',
          }}
        >
          {tag}
        </CheckableTag>
      ))}
    </Space>
  );
}
