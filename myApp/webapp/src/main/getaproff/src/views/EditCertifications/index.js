import React, { useRef } from 'react';
import PropTypes from 'prop-types';

import { Wrapper, MainContainer, Title } from "../../GlobalStyle";
import Navbar from '../../components/Navbar';
import { ButtonContainer, Content, Files } from './EditCertifications.styles';
import Rows from '../../components/Rows';
import Button from '../../components/Button';

const EditCertifications = () => {
  const inputFile = useRef(null);
  const files = ['File 1', 'File 2', 'File 3'];

  const openFile = () => {
    inputFile.current.click();
  };

  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>Edit certifications</Title>
          <Files>
            {files.map((item, index) => {
              return <Rows key={index} data={item} rowId={index} type={1}/>
            })}
          </Files>
          <ButtonContainer>
            <label>
              <Button text="Upload file" fontSize="1rem" callback={openFile}/>
              <input
                type="file"
                id='certification'
                ref={inputFile}
              />
            </label>
            <Button text="Save changes" fontSize="1rem"/>
          </ButtonContainer>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

EditCertifications.propTypes = {};

export default EditCertifications;
