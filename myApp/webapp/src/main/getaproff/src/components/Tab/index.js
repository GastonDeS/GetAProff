import React, { useState } from 'react'
import PropTypes from 'prop-types'

import styled from 'styled-components';

const TabContainer = styled.div`
  display: flex;
  width: 100%;
  align-items: stretch;
`;

const Tab = ({ children, setIndex, setValue, flexDirection }) => {
  const [itemId, setItemId] = useState(0);

  return (
    <TabContainer style={{ flexDirection: flexDirection}}>
      {React.Children.map(children, (child, index) => {
        return React.cloneElement(child, {
          onClick: () => {
            setItemId(index);
            setIndex(index);
            setValue("Role", 1 - index);
          },
          selected: itemId === index
        });
      })}
    </TabContainer>
  );
};

Tab.propTypes = {
  flexDirection: PropTypes.string
}

Tab.defaultProps = {
  flexDirection: 'row'
};

export default Tab
