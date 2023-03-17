import React, { useState } from 'react';

const JoinSelectChange = (
  event: React.ChangeEvent<HTMLSelectElement>,
  props: any,
): void => {
  props(event.target.value);
  console.log(event.target.value);
};

export default JoinSelectChange;
