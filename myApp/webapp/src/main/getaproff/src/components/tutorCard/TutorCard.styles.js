import styled from 'styled-components';

export const Card = styled.div`
  overflow: hidden;
  background-color: white;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
  cursor: pointer;
  transition: transform 200ms ease-in;
  width: 225px; 
  height: 365px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: flex-start;
  margin: 8px;
  border-radius: 10px;

  &:hover {
    transform: scale(1.1);
    box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.9);
  }
`;

export const CardBody = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 16px;
  height: 100%;
`;

export const TutorImg = styled.img`
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-top-left-radius: inherit;
  border-top-right-radius: inherit;
`;

export const Name = styled.h5`
  margin: 0 0 0.5rem 0;
  font-size: 1.25rem;
  font-weight: 500;
`;

export const Description = styled.p`
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;    
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3; /* number of lines to show */
  text-align: start;
  font-size: var(--fontSmall);
  margin-bottom: 8px;
`;

export const Price = styled.p`
  margin-top: 5px;
  font-size: 0.8em;
`;